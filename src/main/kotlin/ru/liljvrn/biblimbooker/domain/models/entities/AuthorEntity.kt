package ru.liljvrn.biblimbooker.domain.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("authors")
data class AuthorEntity(
    val authorName: String,
    val authorDescription: String,
    val authorPhotoUrl: String
) {
    @Id
    var authorId: Long? = null
}
