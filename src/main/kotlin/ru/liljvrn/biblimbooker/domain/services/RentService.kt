package ru.liljvrn.biblimbooker.domain.services

import org.springframework.stereotype.Service
import ru.liljvrn.biblimbooker.domain.components.async.RentAsyncComponent
import ru.liljvrn.biblimbooker.domain.components.transactional.BookTransactionalComponent
import ru.liljvrn.biblimbooker.domain.components.transactional.RentTransactionalComponent
import ru.liljvrn.biblimbooker.domain.models.dto.Rent
import ru.liljvrn.biblimbooker.domain.models.dto.page.RentedBookPage
import ru.liljvrn.biblimbooker.support.mappers.toDto
import ru.liljvrn.biblimbooker.support.pageRequest
import java.util.*

@Service
class RentService(
    private val rentComponent: RentTransactionalComponent,
    private val bookComponent: BookTransactionalComponent,
    private val rentAsyncComponent: RentAsyncComponent
) {

    fun getClientRentHistory(page: Int, userId: UUID): RentedBookPage = pageRequest(page) {
        rentComponent.findRentHistory(userId, it.pageSize, it.offset)
    }

    fun acceptClientRent(bookUnitId: UUID): Rent {
        val rent = rentComponent.createRent(bookUnitId).toDto()
        val book = bookComponent.findBook(rent.bookId)

        rentAsyncComponent.sendStartRentNotification(book, rent)

        return rent
    }

    fun endClientRent(bookUnitId: UUID): Rent = rentComponent.endRent(bookUnitId).toDto()

    fun notifyEndRent() {
        val rents = rentComponent.findEndingRents()

        for (rent in rents) {
            val book = bookComponent.findBook(rent.bookId)
            rentAsyncComponent.sendEndRentNotification(book, rent)
        }
    }

    fun notifyOverdueRent() {
        val rents = rentComponent.findOverdueRents()

        for (rent in rents) {
            val book = bookComponent.findBook(rent.bookId)
            rentAsyncComponent.sendOverdueRentNotification(book, rent)
        }
    }
}
