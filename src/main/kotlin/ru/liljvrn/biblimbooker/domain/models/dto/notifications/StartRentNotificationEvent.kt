package ru.liljvrn.biblimbooker.domain.models.dto.notifications

import ru.liljvrn.biblimbooker.domain.models.types.NotificationType
import java.time.LocalDate

data class StartRentNotificationEvent(
    override val email: String,
    override val eventType: NotificationType,
    val firstName: String,
    val bookName: String,
    val authorName: String,
    val dueDate: LocalDate,
    val currentDate: LocalDate
) : Event
