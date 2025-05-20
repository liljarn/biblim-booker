package ru.liljvrn.biblimbooker.support.mappers

import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.data.domain.Page
import ru.liljvrn.biblimbooker.domain.models.dto.Author
import ru.liljvrn.biblimbooker.domain.models.dto.AuthorEmbeddingModel
import ru.liljvrn.biblimbooker.domain.models.dto.Book
import ru.liljvrn.biblimbooker.domain.models.dto.BookEmbeddingModel
import ru.liljvrn.biblimbooker.domain.models.dto.Comment
import ru.liljvrn.biblimbooker.domain.models.dto.Genre
import ru.liljvrn.biblimbooker.domain.models.dto.UserData
import ru.liljvrn.biblimbooker.domain.models.dto.page.AuthorPage
import ru.liljvrn.biblimbooker.domain.models.dto.page.BookPage
import ru.liljvrn.biblimbooker.domain.models.dto.page.CommentPage
import ru.liljvrn.biblimbooker.domain.models.dto.page.EmbeddingPage
import ru.liljvrn.biblimbooker.domain.models.entities.AuthorEntity
import ru.liljvrn.biblimbooker.domain.models.entities.CommentEntity
import ru.liljvrn.biblimbooker.support.JsonUtils
import java.math.BigDecimal

fun Page<AuthorEntity>.toPage(): AuthorPage = AuthorPage(
    total = totalElements,
    authors = content.map { it.toDto() }
)

fun EmbeddingPage.toAuthorPage(): AuthorPage {
    val responses = models.map { model ->
        when (model) {
            is AuthorEmbeddingModel -> Author(
                authorId = model.authorId.toLong(),
                authorName = model.authorName,
                authorDescription = model.authorDescription,
                authorPhotoUrl = model.authorPhotoUrl,
            )
            else -> throw IllegalArgumentException("Unknown model type: ${model::class}")
        }
    }
    return AuthorPage(
        total = total,
        authors = responses
    )
}

fun EmbeddingPage.toBookPage(): BookPage {
    val responses = models.map { model ->
        when (model) {
            is BookEmbeddingModel -> Book(
                bookId = model.bookId.toLong(),
                bookName = model.bookName,
                authorId = model.authorId.toLong(),
                authorName = model.authorName,
                authorPhotoUrl = model.authorPhotoUrl,
                releaseYear = model.releaseYear.toShort(),
                ageLimit = model.ageLimit.toShort(),
                description = model.description,
                photoUrl = model.photoUrl,
                rating = BigDecimal(model.rating),
                downloadable = model.downloadable.toBoolean(),
                genres = JsonUtils.mapper.readValue(
                    model.genres,
                    object : TypeReference<ArrayList<Genre>>() {}
                )
            )
            else -> throw IllegalArgumentException("Unknown model type: ${model::class}")
        }
    }
    return BookPage(
        total = total,
        books = responses
    )
}

fun Page<CommentEntity>.toPage(usersData: List<UserData>): CommentPage = CommentPage(
    total = totalElements,
    comments = content.mapIndexed { index, comment ->
        comment.toDto(usersData[index])
    }
)
