package ru.liljvrn.biblimbooker.domain.repositories

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ru.liljvrn.biblimbooker.domain.models.entities.BookUnitEntity
import ru.liljvrn.biblimbooker.domain.models.types.BookStatus
import java.util.UUID

interface BookUnitRepository : CrudRepository<BookUnitEntity, UUID> {

    @Modifying
    @Query(
        """
        INSERT INTO book_units (book_unit_id, book_id)
        VALUES (:#{#unit.bookUnitId}, :#{#unit.bookId})
        """
    )
    fun addUnit(@Param("unit") bookUnitEntity: BookUnitEntity)

    fun findByBookId(bookId: Long): List<BookUnitEntity>

    @Modifying
    @Query("UPDATE book_units SET status = :status WHERE book_unit_id = :id")
    fun updateStatus(@Param("status") status: BookStatus, @Param("id") bookUnitId: UUID)

    @Modifying
    @Query("""
        UPDATE book_units SET status = :status
        WHERE book_unit_id IN (:ids)
    """)
    fun updateBooksStatus(@Param("ids") ids: List<UUID>, @Param("status") status: BookStatus)
}
