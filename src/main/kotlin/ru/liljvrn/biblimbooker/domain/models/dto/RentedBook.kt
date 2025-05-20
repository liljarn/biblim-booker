package ru.liljvrn.biblimbooker.domain.models.dto

import org.springframework.data.annotation.Reference
import java.math.BigDecimal
import java.time.LocalDate

data class RentedBook(
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
    @Reference
    val genres: ArrayList<Genre>,
    val readDate: LocalDate?
)
