package ru.liljvrn.biblimbooker.api.rest.v1.management

import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.api.rest.json.request.AddAuthorRequest
import ru.liljvrn.biblimbooker.api.rest.json.response.AuthorFullResponse
import ru.liljvrn.biblimbooker.domain.services.AuthorService
import ru.liljvrn.biblimbooker.support.mappers.toDto
import ru.liljvrn.biblimbooker.support.mappers.toFullResponse
import ru.liljvrn.biblimbooker.support.reflection.ManagementApi

@RestController
@RequestMapping("/api/v1/management/author")
@ManagementApi
class AuthorManagementController(
    private val authorService: AuthorService
) {

    @PostMapping
    fun addAuthor(@ModelAttribute request: AddAuthorRequest): AuthorFullResponse =
        authorService.addAuthor(request.toDto()).toFullResponse()
}
