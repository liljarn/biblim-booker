package ru.liljvrn.biblimbooker.domain.repositories

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.liljvrn.biblimbooker.domain.models.entities.RentEntity
import java.time.LocalDate
import java.util.*

@Repository
interface RentRepository : CrudRepository<RentEntity, UUID> {

    @Modifying
    @Query(
        """
        INSERT INTO book_rent_queue (rent_id, book_id, book_unit_id, user_id, due_date)
        VALUES (:#{#rent.rentId}, :#{#rent.bookId}, :#{#rent.bookUnitId}, :#{#rent.userId}, :#{#rent.dueDate})
        """
    )
    fun makeRent(@Param("rent") rentEntity: RentEntity)

    fun findByBookUnitIdAndDeletedAtIsNull(bookUnitId: UUID): RentEntity

    fun existsByBookIdAndUserIdAndDeletedAtIsNull(bookId: Long, userId: UUID): Boolean

    fun findByUserIdAndDeletedAtIsNull(userId: UUID): RentEntity?

    fun existsByUserIdAndDeletedAtIsNull(userId: UUID): Boolean

    @Query(
        """
            SELECT * FROM book_rent_queue brq
            WHERE brq.due_date > :startDate
            AND brq.due_date <= :endDate
            AND brq.deleted_at IS NULL
        """
    )
    fun findByDueDateBetweenAndDeletedAtIsNull(startDate: LocalDate, endDate: LocalDate): List<RentEntity>

    @Query("""
            SELECT * FROM book_rent_queue brq
            WHERE brq.due_date <= :date
            AND brq.deleted_at IS NULL
    """)
    fun findByDueDateBeforeAndDeletedAtIsNull(date: LocalDate): List<RentEntity>
}
