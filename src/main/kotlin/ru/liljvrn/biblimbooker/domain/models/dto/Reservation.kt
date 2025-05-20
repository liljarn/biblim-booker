package ru.liljvrn.biblimbooker.domain.models.dto

import java.time.LocalDate
import java.util.*

data class Reservation(
    val reservationId: UUID,
    val bookId: Long,
    val bookUnitId: UUID,
    val userId: UUID,
    val dueDate: LocalDate,
)
