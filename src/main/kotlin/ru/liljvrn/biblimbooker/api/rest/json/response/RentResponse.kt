package ru.liljvrn.biblimbooker.api.rest.json.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class RentResponse(
    val rentId: UUID,
    val bookId: Long,
    val bookUnitId: UUID,
    val userId: UUID,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val dueDate: LocalDate,
    var deletedAt: LocalDateTime?
)
