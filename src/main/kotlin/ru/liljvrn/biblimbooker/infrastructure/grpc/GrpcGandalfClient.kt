package ru.liljvrn.biblimbooker.infrastructure.grpc

import kotlinx.coroutines.runBlocking
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service
import ru.liljarn.gandalf.user.UserDataResponse
import ru.liljarn.gandalf.user.UserDataServiceGrpcKt
import ru.liljarn.gandalf.user.getUserDataIdRequest
import ru.liljarn.gandalf.user.getUserDataJwtRequest
import ru.liljvrn.biblimbooker.domain.clients.GandalfClient
import ru.liljvrn.biblimbooker.domain.models.dto.UserData
import java.time.Instant
import java.time.ZoneOffset
import java.util.*

@Service
class GrpcGandalfClient : GandalfClient {

    @GrpcClient("gandalf")
    private lateinit var grpcClient: UserDataServiceGrpcKt.UserDataServiceCoroutineStub

    override fun getUserByToken(token: String) = runBlocking {
        grpcClient.getUserDataByJwt(getUserDataJwtRequest { jwt = token })
    }.toDomain()

    override fun getUserById(uuid: UUID) = runBlocking {
        grpcClient.getUserDataById(getUserDataIdRequest { this.uuid = uuid.toString() })
    }.toDomain()

    private fun UserDataResponse.toDomain(): UserData = UserData(
        userId = UUID.fromString(uuid),
        email = email,
        firstName = firstName,
        lastName = lastName,
        birthDate = Instant.ofEpochSecond(birthdate.seconds, birthdate.nanos.toLong()).atZone(ZoneOffset.UTC).toLocalDate(),
        photoUrl = photoUrl,
    )
}
