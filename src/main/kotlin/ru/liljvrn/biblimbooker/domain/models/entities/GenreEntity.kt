package ru.liljvrn.biblimbooker.domain.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("genres")
data class GenreEntity(
    val genreName: String
) {
    @Id
    var genreId: Int? = null
}
