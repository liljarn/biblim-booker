package ru.liljvrn.biblimbooker.api.rest.json.request

import org.springframework.web.multipart.MultipartFile

data class AddBookRequest(
    val bookName: String,
    val releaseYear: String,
    val ageLimit: String,
    val description: String,
    val units: String,
    val genres: List<Int>,
    val photo: MultipartFile,
    val source: MultipartFile? = null,
)
