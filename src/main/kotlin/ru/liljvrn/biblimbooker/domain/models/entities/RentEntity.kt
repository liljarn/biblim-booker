package ru.liljvrn.biblimbooker.domain.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Table("book_rent_queue")
data class RentEntity(
    @Id
    val rentId: UUID,
    val bookId: Long,
    val bookUnitId: UUID,
    val userId: UUID,
    val dueDate: LocalDate,
) {
    var deletedAt: LocalDateTime? = null

    fun softDelete() {
        deletedAt = LocalDateTime.now()
    }
}
