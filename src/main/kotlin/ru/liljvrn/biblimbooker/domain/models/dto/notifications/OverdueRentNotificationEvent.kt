package ru.liljvrn.biblimbooker.domain.models.dto.notifications

import ru.liljvrn.biblimbooker.domain.models.types.NotificationType

data class OverdueRentNotificationEvent(
    override val email: String,
    override val eventType: NotificationType,
    val firstName: String,
    val bookName: String,
    val authorName: String,
) : Event
