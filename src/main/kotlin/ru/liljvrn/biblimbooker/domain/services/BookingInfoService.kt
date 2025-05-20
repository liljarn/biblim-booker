package ru.liljvrn.biblimbooker.domain.services

import org.springframework.stereotype.Service
import ru.liljvrn.biblimbooker.domain.components.transactional.BookTransactionalComponent
import ru.liljvrn.biblimbooker.domain.components.transactional.RentTransactionalComponent
import ru.liljvrn.biblimbooker.domain.components.transactional.ReservationTransactionalComponent
import ru.liljvrn.biblimbooker.domain.models.dto.BookingInfo
import ru.liljvrn.biblimbooker.support.mappers.toDto
import java.util.*

@Service
class BookingInfoService(
    private val bookComponent: BookTransactionalComponent,
    private val reservationComponent: ReservationTransactionalComponent,
    private val rentComponent: RentTransactionalComponent
) {

    fun getClientCurrentBookingInfo(userId: UUID): BookingInfo? =
        findBookInfoWithReservation(userId) ?: findBookInfoWithRent(userId)

    private fun findBookInfoWithReservation(userId: UUID): BookingInfo? =
        reservationComponent.findClientReservation(userId)?.toDto()?.let {
            BookingInfo(
                book = bookComponent.findBook(it.bookId),
                dueDate = it.dueDate
            )
        }

    private fun findBookInfoWithRent(userId: UUID): BookingInfo? =
        rentComponent.findClientRent(userId)?.toDto()?.let {
            BookingInfo(
                book = bookComponent.findBook(it.bookId),
                dueDate = it.dueDate
            )
        }
}
