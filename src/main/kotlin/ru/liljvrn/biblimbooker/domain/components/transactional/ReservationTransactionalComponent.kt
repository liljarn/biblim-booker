package ru.liljvrn.biblimbooker.domain.components.transactional

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import ru.liljvrn.biblimbooker.domain.models.entities.ReservationEntity
import ru.liljvrn.biblimbooker.domain.models.types.BookStatus
import ru.liljvrn.biblimbooker.domain.repositories.BookUnitRepository
import ru.liljvrn.biblimbooker.domain.repositories.RentRepository
import ru.liljvrn.biblimbooker.domain.repositories.ReservationRepository
import ru.liljvrn.biblimbooker.support.security.user
import java.time.LocalDate
import java.util.*

@Component
class ReservationTransactionalComponent(
    private val reservationRepository: ReservationRepository,
    private val rentRepository: RentRepository,
    private val bookUnitRepository: BookUnitRepository,
) {

    @Transactional
    fun addReservation(bookId: Long): ReservationEntity {
        if (reservationRepository.existsByUserId(user.userId)) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "User already have reservation"
        )
        if (rentRepository.existsByUserIdAndDeletedAtIsNull(user.userId)) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "User already have rent"
        )

        val bookUnit = bookUnitRepository.findByBookId(bookId).first { it.status == BookStatus.AVAILABLE }
        val dueDate = LocalDate.now().plusDays(3)

        val entity = ReservationEntity(
            reservationId = UUID.randomUUID(),
            bookId = bookId,
            bookUnitId = bookUnit.bookUnitId,
            userId = user.userId,
            dueDate = dueDate
        )
        reservationRepository.makeReservation(entity)
        bookUnitRepository.save(bookUnit.apply { status = BookStatus.BOOKED })
        return entity
    }

    @Transactional
    fun deleteReservation(): ReservationEntity {
        val reservation = reservationRepository.findByUserId(user.userId) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Reservation not found"
        )
        bookUnitRepository.updateStatus(BookStatus.AVAILABLE, reservation.bookUnitId)

        reservationRepository.delete(reservation)
        return reservation
    }

    @Transactional
    fun deleteOverdueReservation() {
        val dueData = LocalDate.now()
        val reservations = reservationRepository.findByDueDateBefore(dueData)

        if (reservations.isNotEmpty()) {
            reservationRepository.deleteOverdueReservations(dueData)
            bookUnitRepository.updateBooksStatus(reservations.map { it.bookUnitId }, BookStatus.AVAILABLE)
        }
    }

    @Transactional(readOnly = true)
    fun findClientReservation(userId: UUID): ReservationEntity? = reservationRepository.findByUserId(userId)
}
