package ru.liljvrn.biblimbooker.support.mappers

import ru.liljvrn.biblimbooker.api.rest.json.response.AuthorFullResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.AuthorPageResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.AuthorResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.BookPageResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.BookResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.CommentPageResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.CommentResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.GenreResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.RentResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.RentedBookPageResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.RentedBookResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.ReservationResponse
import ru.liljvrn.biblimbooker.domain.models.dto.Author
import ru.liljvrn.biblimbooker.domain.models.dto.Book
import ru.liljvrn.biblimbooker.domain.models.dto.Comment
import ru.liljvrn.biblimbooker.domain.models.dto.Genre
import ru.liljvrn.biblimbooker.domain.models.dto.Rent
import ru.liljvrn.biblimbooker.domain.models.dto.RentedBook
import ru.liljvrn.biblimbooker.domain.models.dto.Reservation
import ru.liljvrn.biblimbooker.domain.models.dto.page.AuthorPage
import ru.liljvrn.biblimbooker.domain.models.dto.page.BookPage
import ru.liljvrn.biblimbooker.domain.models.dto.page.CommentPage
import ru.liljvrn.biblimbooker.domain.models.dto.page.RentedBookPage
import java.util.*

fun AuthorPage.toResponse(): AuthorPageResponse = AuthorPageResponse(
    total = total,
    authors = authors.map { it.toResponse() }
)

fun Author.toResponse(): AuthorResponse = AuthorResponse(
    authorId = authorId,
    authorName = authorName,
    authorPhotoUrl = authorPhotoUrl,
)

fun Author.toFullResponse(): AuthorFullResponse = AuthorFullResponse(
    authorId = authorId,
    authorName = authorName,
    authorDescription = authorDescription,
    authorPhotoUrl = authorPhotoUrl,
)

fun Genre.toResponse(): GenreResponse = GenreResponse(
    genreId = genreId,
    genreName = genreName,
)

fun Book.toResponse(): BookResponse = BookResponse(
    bookId = bookId,
    bookName = bookName,
    authorId = authorId,
    authorName = authorName,
    authorPhotoUrl = authorPhotoUrl,
    releaseYear = releaseYear,
    ageLimit = ageLimit,
    description = description,
    photoUrl = photoUrl,
    downloadable = downloadable,
    rating = rating,
    genres = genres
)

fun BookPage.toResponse(): BookPageResponse = BookPageResponse(
    total = total,
    books = books.map { it.toResponse() }
)

fun Reservation.toResponse(): ReservationResponse = ReservationResponse(
    reservationId = reservationId,
    bookId = bookId,
    bookUnitId = bookUnitId,
    userId = userId,
    dueDate = dueDate,
)

fun Rent.toResponse(): RentResponse = RentResponse(
    rentId = rentId,
    bookId = bookId,
    bookUnitId = bookUnitId,
    userId = userId,
    dueDate = dueDate,
    deletedAt = deletedAt,
)

fun Comment.toResponse(userId: UUID?): CommentResponse = CommentResponse(
    commentId = commentId,
    bookId = bookId,
    comment = comment,
    rating = rating,
    userData = userData,
    self = userId == userData.userId,
)

fun CommentPage.toResponse(userId: UUID?): CommentPageResponse = CommentPageResponse(
    total = total,
    comments = comments.map { it.toResponse(userId) }
)

fun RentedBook.toResponse(): RentedBookResponse = RentedBookResponse(
    bookId = bookId,
    bookName = bookName,
    authorId = authorId,
    authorName = authorName,
    authorPhotoUrl = authorPhotoUrl,
    releaseYear = releaseYear,
    ageLimit = ageLimit,
    description = description,
    photoUrl = photoUrl,
    rating = rating,
    genres = genres,
    downloadable = downloadable,
    readDate = readDate,
)

fun RentedBookPage.toResponse(): RentedBookPageResponse = RentedBookPageResponse(
    total = total,
    rentedBooks = rentedBooks.map { it.toResponse() }
)
