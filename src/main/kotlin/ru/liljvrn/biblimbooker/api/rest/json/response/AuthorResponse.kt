package ru.liljvrn.biblimbooker.api.rest.json.response

data class AuthorResponse(
    val authorId: Long,
    val authorName: String,
    val authorPhotoUrl: String
)

data class AuthorPageResponse(
    val total: Long,
    val authors: List<AuthorResponse>
)

data class AuthorFullResponse(
    val authorId: Long,
    val authorName: String,
    val authorDescription: String,
    val authorPhotoUrl: String
)
