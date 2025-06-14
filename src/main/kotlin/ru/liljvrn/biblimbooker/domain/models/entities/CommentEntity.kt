package ru.liljvrn.biblimbooker.domain.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("comments")
data class CommentEntity(
    @Id
    val commentId : UUID,
    val userId : UUID,
    val comment: String,
    val rating: Int,
    val bookId: Long
)
