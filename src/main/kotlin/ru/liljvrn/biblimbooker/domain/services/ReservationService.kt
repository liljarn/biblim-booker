package ru.liljvrn.biblimbooker.domain.services

import org.springframework.stereotype.Service
import ru.liljvrn.biblimbooker.domain.clients.OkarunClient
import ru.liljvrn.biblimbooker.domain.components.transactional.BookTransactionalComponent
import ru.liljvrn.biblimbooker.domain.components.transactional.ReservationTransactionalComponent
import ru.liljvrn.biblimbooker.domain.models.dto.Reservation
import ru.liljvrn.biblimbooker.domain.models.dto.notifications.NewReservationNotificationEvent
import ru.liljvrn.biblimbooker.domain.models.types.NotificationType
import ru.liljvrn.biblimbooker.support.mappers.toDto
import ru.liljvrn.biblimbooker.support.security.user

@Service
class ReservationService(
    private val okarunClient: OkarunClient,
    private val reservationComponent: ReservationTransactionalComponent,
    private val bookComponent: BookTransactionalComponent
) {

    fun reserveBook(bookId: Long): Reservation {
        val reservation = reservationComponent.addReservation(bookId).toDto()
        val book = bookComponent.findBook(bookId)
        okarunClient.sendNotification(
            NewReservationNotificationEvent(
                email = user.email,
                eventType = NotificationType.NEW_RESERVATION,
                firstName = user.firstName,
                bookName = book.bookName,
                authorName = book.authorName,
                dueDate = reservation.dueDate,
            )
        )
        return reservation
    }

    fun removeClientReservation(): Reservation {
        return reservationComponent.deleteReservation().toDto()
    }

    fun removeOverdueReservation() {
        reservationComponent.deleteOverdueReservation()
    }
}
