package ru.liljvrn.biblimbooker.domain.models.dto

import java.io.InputStream

data class AddAuthor(
    val authorName: String,
    val authorDescription: String,
    val authorPhoto: InputStream
)
