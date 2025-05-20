package ru.liljvrn.biblimbooker.api.rest.json.response

import ru.liljvrn.biblimbooker.domain.models.dto.UserData
import java.util.*

data class CommentResponse(
    val commentId: UUID,
    val userData: UserData,
    val comment: String,
    val rating: Int,
    val bookId: Long,
    val self: Boolean
)

data class CommentPageResponse(
    val total: Long,
    val comments: List<CommentResponse>
)
