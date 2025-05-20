package ru.liljvrn.biblimbooker.domain.models.dto.page

import ru.liljvrn.biblimbooker.domain.models.dto.Author

data class AuthorPage(
    val total: Long,
    val authors: List<Author>
)
