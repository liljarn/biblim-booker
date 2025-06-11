package ru.liljvrn.biblimbooker.domain.components.async

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.domain.clients.GandalfClient
import ru.liljvrn.biblimbooker.domain.clients.OkarunClient
import ru.liljvrn.biblimbooker.domain.models.dto.Book
import ru.liljvrn.biblimbooker.domain.models.dto.Rent
import ru.liljvrn.biblimbooker.domain.models.dto.notifications.EndRentNotificationEvent
import ru.liljvrn.biblimbooker.domain.models.dto.notifications.OverdueRentNotificationEvent
import ru.liljvrn.biblimbooker.domain.models.dto.notifications.StartRentNotificationEvent
import ru.liljvrn.biblimbooker.domain.models.types.NotificationType
import java.time.LocalDate

@Component
class RentAsyncComponent(
    private val gandalfClient: GandalfClient,
    private val okarunClient: OkarunClient
) {

    @Async
    fun sendStartRentNotification(book: Book, rent: Rent) {
        val userData = gandalfClient.getUserById(rent.userId)
        okarunClient.sendNotification(
            StartRentNotificationEvent(
                email = userData.email,
                eventType = NotificationType.START_RENT,
                bookName = book.bookName,
                firstName = userData.firstName,
                authorName = book.authorName,
                dueDate = rent.dueDate,
                currentDate = LocalDate.now(),
            )
        )
    }

    @Async
    fun sendEndRentNotification(book: Book, rent: Rent) {
        val userData = gandalfClient.getUserById(rent.userId)
        okarunClient.sendNotification(
            EndRentNotificationEvent(
                userData.email,
                NotificationType.END_RENT,
                userData.firstName,
                book.bookName,
                book.authorName,
                rent.dueDate
            )
        )
    }

    @Async
    fun sendOverdueRentNotification(book: Book, rent: Rent) {
        val userData = gandalfClient.getUserById(rent.userId)
        okarunClient.sendNotification(
            OverdueRentNotificationEvent(
                userData.email,
                NotificationType.OVERDUE_RENT,
                userData.firstName,
                book.bookName,
                book.authorName
            )
        )
    }
}
