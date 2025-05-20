package ru.liljvrn.biblimbooker.domain.clients

import ru.liljvrn.biblimbooker.domain.models.types.ImageType
import java.io.InputStream

interface ImageClient {
    fun uploadImage(file: InputStream, name: String, type: ImageType): String

    fun getImageUrl(name: String, type: ImageType): String
}
