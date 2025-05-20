package ru.liljvrn.biblimbooker.domain.models.dto

import ru.liljvrn.biblimbooker.domain.models.types.BookStatus

data class BookDetailed(
    val book: Book,
    val status: BookStatus
)
