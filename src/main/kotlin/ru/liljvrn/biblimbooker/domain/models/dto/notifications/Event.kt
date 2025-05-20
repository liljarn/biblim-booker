package ru.liljvrn.biblimbooker.domain.models.dto.notifications

import ru.liljvrn.biblimbooker.domain.models.types.NotificationType

interface Event {
    val email: String
    val eventType: NotificationType
}
