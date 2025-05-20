package ru.liljvrn.biblimbooker.api.rest.v1.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.api.rest.json.response.AuthorFullResponse
import ru.liljvrn.biblimbooker.api.rest.json.response.AuthorPageResponse
import ru.liljvrn.biblimbooker.domain.services.AuthorService
import ru.liljvrn.biblimbooker.support.mappers.toFullResponse
import ru.liljvrn.biblimbooker.support.mappers.toResponse

@RestController
@RequestMapping("/api/v1/author")
class AuthorController(
    private val authorService: AuthorService
) {

    @GetMapping("/list")
    fun getAuthors(
        @RequestParam page: Int = 0,
        @RequestParam query: String?
    ): AuthorPageResponse = authorService.getAuthors(page, query).toResponse()

    @GetMapping("/{authorId}")
    fun getAuthorById(@PathVariable authorId: Long): AuthorFullResponse =
        authorService.getAuthorInfo(authorId).toFullResponse()
}
