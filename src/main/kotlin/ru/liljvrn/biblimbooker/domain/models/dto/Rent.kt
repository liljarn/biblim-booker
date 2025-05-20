package ru.liljvrn.biblimbooker.domain.models.dto

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Rent(
    val rentId: UUID,
    val bookId: Long,
    val bookUnitId: UUID,
    val userId: UUID,
    val dueDate: LocalDate,
    var deletedAt: LocalDateTime?
)
