package ru.liljvrn.biblimbooker.domain.components.transactional

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.liljvrn.biblimbooker.domain.models.dto.BookUnit
import ru.liljvrn.biblimbooker.domain.models.types.BookStatus
import ru.liljvrn.biblimbooker.domain.repositories.BookUnitRepository
import ru.liljvrn.biblimbooker.domain.repositories.RentRepository
import ru.liljvrn.biblimbooker.domain.repositories.ReservationRepository
import ru.liljvrn.biblimbooker.support.mappers.toDto

@Component
class BookUnitTransactionalComponent(
    private val bookUnitRepository: BookUnitRepository,
    private val rentRepository: RentRepository,
    private val reservationRepository: ReservationRepository
) {

    @Transactional(readOnly = true)
    fun findBookUnits(bookId: Long): List<BookUnit> {
        val units = bookUnitRepository.findByBookId(bookId)

        return units.map {
            val userId = when (it.status) {
                BookStatus.BOOKED -> reservationRepository.findByBookUnitId(it.bookUnitId).userId
                BookStatus.READING -> rentRepository.findByBookUnitIdAndDeletedAtIsNull(it.bookUnitId).userId
                else -> null
            }
            it.toDto(userId)
        }
    }
}
