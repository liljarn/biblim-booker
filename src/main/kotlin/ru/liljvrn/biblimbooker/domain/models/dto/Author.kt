package ru.liljvrn.biblimbooker.domain.models.dto

data class Author(
    val authorId: Long,
    val authorName: String,
    val authorDescription: String,
    val authorPhotoUrl: String
)
