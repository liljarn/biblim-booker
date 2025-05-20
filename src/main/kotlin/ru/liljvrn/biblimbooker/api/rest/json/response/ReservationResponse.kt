package ru.liljvrn.biblimbooker.api.rest.json.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.util.*

data class ReservationResponse(
    val reservationId: UUID,
    val bookId: Long,
    val bookUnitId: UUID,
    val userId: UUID,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val dueDate: LocalDate,
)
