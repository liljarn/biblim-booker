package ru.liljvrn.biblimbooker.infrastructure.s3

import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.domain.clients.ImageClient
import ru.liljvrn.biblimbooker.domain.models.types.ImageType
import ru.liljvrn.biblimbooker.infrastructure.properties.S3Properties
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ObjectCannedACL
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.InputStream

@Component
class S3ImageClient(
    private val s3Client: S3Client,
    private val properties: S3Properties,
) : ImageClient {

    override fun uploadImage(file: InputStream, name: String, type: ImageType): String =
        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(properties.bucketName[type.value])
                .acl(ObjectCannedACL.PUBLIC_READ)
                .key(name)
                .build(),
            RequestBody.fromBytes(file.readBytes())
        ).let {
            getImageUrl(name, type)
        }

    override fun getImageUrl(name: String, type: ImageType): String =
        "https://storage.yandexcloud.net/${properties.bucketName[type.value]}/" +
                name.replace(" ", "%20")
}
