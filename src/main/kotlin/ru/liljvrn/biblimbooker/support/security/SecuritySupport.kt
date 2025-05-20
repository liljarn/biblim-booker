package ru.liljvrn.biblimbooker.support.security

import ru.liljvrn.biblimbooker.domain.models.dto.UserData

inline fun <reified T> userContext(body: (UserData) -> T) = body(user)

inline fun <reified T> softUserContext(body: (UserData?) -> T) = body(nullableUser)
