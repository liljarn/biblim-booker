package ru.liljvrn.biblimbooker.domain.models.dto

import java.io.InputStream

data class PdfBookRequest(
    val bookId: Long,
    val book: InputStream
)
