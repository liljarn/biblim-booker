package ru.liljvrn.biblimbooker.domain.models.dto

import java.io.InputStream

data class AddBook(
    val bookName: String,
    val releaseYear: Short,
    val ageLimit: Short,
    val description: String,
    val units: Short,
    val genres: List<Int>,
    val photo: InputStream,
    val source: InputStream? = null,
)
