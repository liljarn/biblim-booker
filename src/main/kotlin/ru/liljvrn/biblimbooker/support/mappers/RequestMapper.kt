package ru.liljvrn.biblimbooker.support.mappers

import ru.liljvrn.biblimbooker.api.rest.json.request.AddAuthorRequest
import ru.liljvrn.biblimbooker.api.rest.json.request.AddBookRequest
import ru.liljvrn.biblimbooker.api.rest.json.request.AddCommentRequest
import ru.liljvrn.biblimbooker.api.rest.json.request.AddGenreRequest
import ru.liljvrn.biblimbooker.domain.models.dto.AddAuthor
import ru.liljvrn.biblimbooker.domain.models.dto.AddBook
import ru.liljvrn.biblimbooker.domain.models.dto.AddComment
import ru.liljvrn.biblimbooker.domain.models.dto.AddGenre
import ru.liljvrn.biblimbooker.domain.models.dto.Book
import ru.liljvrn.biblimbooker.domain.models.dto.PdfBookRequest
import ru.liljvrn.biblimbooker.domain.models.dto.ReportRequest
import java.util.UUID

fun AddAuthorRequest.toDto(): AddAuthor = AddAuthor(
    authorName = authorName,
    authorDescription = authorDescription,
    authorPhoto = authorPhoto.inputStream
)

fun AddGenreRequest.toDto(): AddGenre = AddGenre(
    genre = genre
)

fun AddBookRequest.toDto(): AddBook = AddBook(
    bookName = bookName,
    releaseYear = releaseYear.toShort(),
    ageLimit = ageLimit.toShort(),
    description = description,
    units = units.toShort(),
    genres = genres,
    photo = photo.inputStream,
    source = source?.inputStream
)

fun AddCommentRequest.toDto(): AddComment = AddComment(
    comment = comment,
    rating = rating,
)
