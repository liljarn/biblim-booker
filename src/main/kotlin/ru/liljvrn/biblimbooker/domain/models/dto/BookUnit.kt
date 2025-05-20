package ru.liljvrn.biblimbooker.domain.models.dto

import ru.liljvrn.biblimbooker.domain.models.types.BookStatus
import java.util.*

data class BookUnit(
    val bookUnitId: UUID,
    val bookId: Long,
    val status: BookStatus,
    val userId: UUID?,
)
