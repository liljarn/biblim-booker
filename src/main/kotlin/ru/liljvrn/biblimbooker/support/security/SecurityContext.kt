package ru.liljvrn.biblimbooker.support.security

import ru.liljvrn.biblimbooker.domain.models.dto.UserData
import ru.liljvrn.biblimbooker.support.security.SecurityContextHolder.securityContext

object SecurityContextHolder {
    val securityContext = ThreadLocal<SecurityContext>()

    fun createContext(user: UserData, jwtToken: String) {
        securityContext.set(SecurityContext(user, jwtToken))
    }
}

class SecurityContext(
    var userInfo: UserData,
    var userJwt: String
)

val user: UserData
    get() = securityContext.get()?.userInfo ?: throw IllegalArgumentException("User not authenticated")

val nullableUser: UserData?
    get() = securityContext.get()?.userInfo
