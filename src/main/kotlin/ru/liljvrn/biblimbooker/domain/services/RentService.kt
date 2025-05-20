package ru.liljvrn.biblimbooker.domain.services

import org.springframework.stereotype.Service
import ru.liljvrn.biblimbooker.domain.clients.GandalfClient
import ru.liljvrn.biblimbooker.domain.clients.OkarunClient
import ru.liljvrn.biblimbooker.domain.components.transactional.BookTransactionalComponent
import ru.liljvrn.biblimbooker.domain.components.transactional.RentTransactionalComponent
import ru.liljvrn.biblimbooker.domain.models.dto.Rent
import ru.liljvrn.biblimbooker.domain.models.dto.notifications.EndRentNotificationEvent
import ru.liljvrn.biblimbooker.domain.models.dto.notifications.OverdueRentNotificationEvent
import ru.liljvrn.biblimbooker.domain.models.dto.notifications.StartRentNotificationEvent
import ru.liljvrn.biblimbooker.domain.models.dto.page.RentedBookPage
import ru.liljvrn.biblimbooker.domain.models.types.NotificationType
import ru.liljvrn.biblimbooker.support.mappers.toDto
import ru.liljvrn.biblimbooker.support.pageRequest
import ru.liljvrn.biblimbooker.support.security.user
import java.time.LocalDate
import java.util.*

@Service
class RentService(
    private val rentComponent: RentTransactionalComponent,
    private val bookComponent: BookTransactionalComponent,
    private val okarunClient: OkarunClient,
    private val gandalfClient: GandalfClient
) {

    fun getClientRentHistory(page: Int, userId: UUID): RentedBookPage = pageRequest(page) {
        rentComponent.findRentHistory(userId, it.pageSize, it.offset)
    }

    fun acceptClientRent(bookUnitId: UUID): Rent {
        val rent = rentComponent.createRent(bookUnitId).toDto()
        val book = bookComponent.findBook(rent.bookId)
        okarunClient.sendNotification(
            StartRentNotificationEvent(
                email = user.email,
                eventType = NotificationType.START_RENT,
                bookName = book.bookName,
                firstName = user.firstName,
                authorName = book.authorName,
                dueDate = rent.dueDate,
                currentDate = LocalDate.now(),
            )
        )

        return rent
    }

    fun endClientRent(bookUnitId: UUID): Rent = rentComponent.endRent(bookUnitId).toDto()

    fun notifyEndRent() {
        val rents = rentComponent.findEndingRents()

        for (rent in rents) {
            val book = bookComponent.findBook(rent.bookId)
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
    }

    fun notifyOverdueRent() {
        val rents = rentComponent.findOverdueRents()

        for (rent in rents) {
            val book = bookComponent.findBook(rent.bookId)
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
}
