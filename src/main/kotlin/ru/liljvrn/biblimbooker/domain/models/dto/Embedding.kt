package ru.liljvrn.biblimbooker.domain.models.dto

sealed class EmbeddingDataModel

data class AuthorEmbeddingModel(
    val authorId: String,
    val authorName: String,
    val authorDescription: String,
    val authorPhotoUrl: String,
) : EmbeddingDataModel()

data class BookEmbeddingModel(
    val bookId: String,
    val bookName: String,
    val authorId: String,
    val authorName: String,
    val authorPhotoUrl: String,
    val releaseYear: String,
    val ageLimit: String,
    val description: String,
    val photoUrl: String,
    val rating: String,
    val downloadable: String,
    val genres: String,
) : EmbeddingDataModel()
