package ru.liljvrn.biblimbooker.domain.clients

import ru.liljvrn.biblimbooker.domain.models.dto.UserData
import java.util.*

interface GandalfClient {

    fun getUserByToken(token: String): UserData

    fun getUserById(uuid: UUID): UserData
}
