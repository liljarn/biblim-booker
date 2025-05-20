package ru.liljvrn.biblimbooker.api.rest.json.request

data class AddCommentRequest(
    val comment: String,
    val rating: Int
)
