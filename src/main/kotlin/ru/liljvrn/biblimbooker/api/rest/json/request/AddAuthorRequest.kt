package ru.liljvrn.biblimbooker.api.rest.json.request

import org.springframework.web.multipart.MultipartFile

data class AddAuthorRequest(
    val authorName: String,
    val authorDescription: String,
    val authorPhoto: MultipartFile
)
