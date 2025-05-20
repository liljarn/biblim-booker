package ru.liljvrn.biblimbooker.domain.models.dto

import java.util.*

data class Comment(
    val commentId: UUID,
    val userData: UserData,
    val comment: String,
    val rating: Int,
    val bookId: Long,
)
