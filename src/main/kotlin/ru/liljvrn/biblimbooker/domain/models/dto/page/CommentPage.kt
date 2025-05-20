package ru.liljvrn.biblimbooker.domain.models.dto.page

import ru.liljvrn.biblimbooker.domain.models.dto.Comment

data class CommentPage(
    val total: Long,
    val comments: List<Comment>
)
