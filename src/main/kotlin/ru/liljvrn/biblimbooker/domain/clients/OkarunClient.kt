package ru.liljvrn.biblimbooker.domain.clients

import ru.liljvrn.biblimbooker.domain.models.dto.notifications.Event

interface OkarunClient {
    fun sendNotification(event: Event)
}
