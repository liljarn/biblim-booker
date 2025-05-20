package ru.liljvrn.biblimbooker.domain.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("books")
data class BookEntity(
    val bookName: String,
    val authorId: Long,
    val releaseYear: Short,
    val ageLimit: Short,
    val description: String,
    val rating: BigDecimal = BigDecimal.ZERO,
    val downloadable: Boolean,
    val photoUrl: String
) {
    @Id
    var bookId: Long? = null
}
