package ru.liljvrn.biblimbooker.domain.models.dto.page

import ru.liljvrn.biblimbooker.domain.models.dto.Book

data class BookPage(
    val total: Long,
    val books: List<Book>
)
