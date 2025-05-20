package ru.liljvrn.biblimbooker.support.mappers

import ru.liljvrn.biblimbooker.domain.models.dto.Author
import ru.liljvrn.biblimbooker.domain.models.dto.AuthorEmbeddingModel
import ru.liljvrn.biblimbooker.domain.models.dto.Book
import ru.liljvrn.biblimbooker.domain.models.dto.BookEmbeddingModel
import ru.liljvrn.biblimbooker.support.JsonUtils

fun Author.toEmbeddingModel(): AuthorEmbeddingModel = AuthorEmbeddingModel(
    authorId = authorId.toString(),
    authorName = authorName,
    authorDescription = authorDescription,
    authorPhotoUrl = authorPhotoUrl,
)

fun Book.toEmbeddingModel(): BookEmbeddingModel = BookEmbeddingModel(
    bookId = bookId.toString(),
    bookName = bookName,
    authorId = authorId.toString(),
    authorName = authorName,
    authorPhotoUrl = authorPhotoUrl,
    releaseYear = releaseYear.toString(),
    ageLimit = ageLimit.toString(),
    description = description,
    photoUrl = photoUrl,
    rating = rating.toString(),
    downloadable = downloadable.toString(),
    genres = JsonUtils.toJson(genres),
)
