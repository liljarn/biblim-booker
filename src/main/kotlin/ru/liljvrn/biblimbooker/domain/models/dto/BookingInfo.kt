package ru.liljvrn.biblimbooker.domain.models.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class BookingInfo(
    val book: Book,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val dueDate: LocalDate,
)
