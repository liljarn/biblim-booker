package ru.liljvrn.biblimbooker.api.rest.json.response

import com.fasterxml.jackson.annotation.JsonFormat
import ru.liljvrn.biblimbooker.domain.models.dto.Genre
import java.math.BigDecimal
import java.time.LocalDate

data class RentedBookResponse(
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
    val genres: ArrayList<Genre>,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val readDate: LocalDate?
)

data class RentedBookPageResponse(
    val total: Long,
    val rentedBooks: List<RentedBookResponse>,
)
