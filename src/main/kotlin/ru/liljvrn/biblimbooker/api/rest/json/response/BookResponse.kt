package ru.liljvrn.biblimbooker.api.rest.json.response

import ru.liljvrn.biblimbooker.domain.models.dto.Genre
import java.math.BigDecimal

data class BookResponse(
    val bookId: Long,
    val bookName: String,
    val authorId: Long,
    val authorName: String,
    val authorPhotoUrl: String,
    val releaseYear: Short,
    val ageLimit: Short,
    val description: String,
    val photoUrl: String,
    val rating: BigDecimal,
    val downloadable: Boolean,
    val genres: ArrayList<Genre>
)

data class BookPageResponse(
    val total: Long,
    val books: List<BookResponse>
)
