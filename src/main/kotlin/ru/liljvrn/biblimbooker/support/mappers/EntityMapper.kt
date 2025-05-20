package ru.liljvrn.biblimbooker.support.mappers

import ru.liljvrn.biblimbooker.domain.models.dto.Author
import ru.liljvrn.biblimbooker.domain.models.dto.BookUnit
import ru.liljvrn.biblimbooker.domain.models.dto.Comment
import ru.liljvrn.biblimbooker.domain.models.dto.Genre
import ru.liljvrn.biblimbooker.domain.models.dto.Rent
import ru.liljvrn.biblimbooker.domain.models.dto.Reservation
import ru.liljvrn.biblimbooker.domain.models.dto.UserData
import ru.liljvrn.biblimbooker.domain.models.entities.AuthorEntity
import ru.liljvrn.biblimbooker.domain.models.entities.BookUnitEntity
import ru.liljvrn.biblimbooker.domain.models.entities.CommentEntity
import ru.liljvrn.biblimbooker.domain.models.entities.GenreEntity
import ru.liljvrn.biblimbooker.domain.models.entities.RentEntity
import ru.liljvrn.biblimbooker.domain.models.entities.ReservationEntity
import java.util.*

fun AuthorEntity.toDto(): Author = Author(
    authorId = authorId ?: throw RuntimeException(),
    authorName = authorName,
    authorDescription = authorDescription,
    authorPhotoUrl = authorPhotoUrl
)

fun GenreEntity.toDto(): Genre = Genre(
    genreId = genreId!!,
    genreName = genreName,
)

fun ReservationEntity.toDto(): Reservation = Reservation(
    reservationId = reservationId,
    bookId = bookId,
    bookUnitId = bookUnitId,
    userId = userId,
    dueDate = dueDate,
)

fun RentEntity.toDto(): Rent = Rent(
    rentId = rentId,
    bookId = bookId,
    bookUnitId = bookUnitId,
    userId = userId,
    dueDate = dueDate,
    deletedAt = deletedAt,
)

fun CommentEntity.toDto(userData: UserData): Comment = Comment(
    commentId = commentId,
    bookId = bookId,
    comment = comment,
    rating = rating,
    userData = userData,
)

fun BookUnitEntity.toDto(userId: UUID?): BookUnit = BookUnit(
    bookId = bookId,
    bookUnitId = bookUnitId,
    status = status,
    userId = userId,
)
