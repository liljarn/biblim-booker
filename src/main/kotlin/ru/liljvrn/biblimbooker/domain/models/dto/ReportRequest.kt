package ru.liljvrn.biblimbooker.domain.models.dto

import java.util.*

data class ReportRequest(
    val bookName: String,
    val authorName: String,
    val releaseYear: Short,
    val ageLimit: Short,
    val description: String,
    val downloadable: Boolean,
    val genres: List<String>,
    val copiesIds: List<UUID>
)
