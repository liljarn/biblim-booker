package ru.liljvrn.biblimbooker.domain.repositories

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.liljvrn.biblimbooker.domain.models.entities.ReservationEntity
import java.time.LocalDate
import java.util.*

@Repository
interface ReservationRepository : CrudRepository<ReservationEntity, UUID> {

    @Modifying
    @Query(
        """
        INSERT INTO book_reservation_queue (reservation_id, book_id, book_unit_id, user_id, due_date)
        VALUES (:#{#req.reservationId}, :#{#req.bookId}, :#{#req.bookUnitId}, :#{#req.userId}, :#{#req.dueDate})
    """
    )
    fun makeReservation(@Param("req") reservationRequest: ReservationEntity)

    @Modifying
    @Query("""
        DELETE FROM book_reservation_queue brq
        WHERE due_date < :dueDate
    """)
    fun deleteOverdueReservations(@Param("dueDate") dueDate: LocalDate)

    fun existsByBookIdAndUserId(bookId: Long, userId: UUID): Boolean

    fun existsByUserId(userId: UUID): Boolean

    fun findByUserId(userId: UUID): ReservationEntity?

    fun findByBookUnitId(bookUnitId: UUID): ReservationEntity

    fun findByDueDateBefore(dueData: LocalDate): List<ReservationEntity>
}
