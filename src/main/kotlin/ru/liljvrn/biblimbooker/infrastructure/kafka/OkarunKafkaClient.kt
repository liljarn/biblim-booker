package ru.liljvrn.biblimbooker.infrastructure.kafka

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.liljvrn.biblimbooker.domain.clients.OkarunClient
import ru.liljvrn.biblimbooker.domain.models.dto.notifications.Event

@Service
class OkarunKafkaClient(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    @Value("\${kafka.topics.notification.destination}")
    private val topic: String
) : OkarunClient {

    override fun sendNotification(event: Event) {
        kafkaTemplate.send(topic, event.email, event)
    }
}
