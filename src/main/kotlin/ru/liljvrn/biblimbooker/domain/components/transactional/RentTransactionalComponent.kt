package ru.liljvrn.biblimbooker.domain.components.transactional

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.liljvrn.biblimbooker.domain.models.dto.Rent
import ru.liljvrn.biblimbooker.domain.models.dto.page.RentedBookPage
import ru.liljvrn.biblimbooker.domain.models.entities.RentEntity
import ru.liljvrn.biblimbooker.domain.models.types.BookStatus
import ru.liljvrn.biblimbooker.domain.repositories.BookRepository
import ru.liljvrn.biblimbooker.domain.repositories.BookUnitRepository
import ru.liljvrn.biblimbooker.domain.repositories.RentRepository
import ru.liljvrn.biblimbooker.domain.repositories.ReservationRepository
import ru.liljvrn.biblimbooker.support.mappers.toDto
import java.time.LocalDate
import java.util.*

@Component
class RentTransactionalComponent(
    private val bookRepository: BookRepository,
    private val rentRepository: RentRepository,
    private val bookUnitRepository: BookUnitRepository,
    private val reservationRepository: ReservationRepository
) {

    @Transactional
    fun createRent(bookUnitId: UUID): RentEntity {
        val reservation = reservationRepository.findByBookUnitId(bookUnitId)
        val dueDate = LocalDate.now().plusMonths(1)
        val rent = RentEntity(
            rentId = UUID.randomUUID(),
            bookId = reservation.bookId,
            userId = reservation.userId,
            bookUnitId = bookUnitId,
            dueDate = dueDate
        )

        rentRepository.makeRent(rent)
        bookUnitRepository.updateStatus(BookStatus.READING, bookUnitId)
        reservationRepository.deleteById(reservation.reservationId)
        return rent
    }

    @Transactional
    fun endRent(bookUnitId: UUID): RentEntity {
        val rent = rentRepository.findByBookUnitIdAndDeletedAtIsNull(bookUnitId)
        rentRepository.save(
            rent.apply { softDelete() }
        )
        bookUnitRepository.updateStatus(BookStatus.AVAILABLE, bookUnitId)
        return rent
    }

    @Transactional(readOnly = true)
    fun findRentHistory(userId: UUID, limit: Int, offset: Long): RentedBookPage {
        val rentedBooks = bookRepository.findPageHistory(userId, limit, offset)
        val total = bookRepository.countHistory(userId)

        return RentedBookPage(
            total = total,
            rentedBooks = rentedBooks
        )
    }

    @Transactional(readOnly = true)
    fun findEndingRents(): List<Rent> {
        val currentDate = LocalDate.now()
        val borderDate = currentDate.plusDays(3)

        val endingRents = rentRepository.findByDueDateBetweenAndDeletedAtIsNull(currentDate, borderDate)

        return endingRents.map { it.toDto() }
    }

    @Transactional(readOnly = true)
    fun findOverdueRents(): List<Rent> {
        val currentDate = LocalDate.now()
        val overdueRents = rentRepository.findByDueDateBeforeAndDeletedAtIsNull(currentDate)

        return overdueRents.map { it.toDto() }
    }

    @Transactional(readOnly = true)
    fun findClientRent(userId: UUID): RentEntity? = rentRepository.findByUserIdAndDeletedAtIsNull(userId)
}
