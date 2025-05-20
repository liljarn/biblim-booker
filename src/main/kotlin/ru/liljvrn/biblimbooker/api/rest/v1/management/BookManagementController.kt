package ru.liljvrn.biblimbooker.api.rest.v1.management

import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.api.rest.json.request.AddBookRequest
import ru.liljvrn.biblimbooker.api.rest.json.response.BookResponse
import ru.liljvrn.biblimbooker.domain.services.BookService
import ru.liljvrn.biblimbooker.support.mappers.toDto
import ru.liljvrn.biblimbooker.support.mappers.toResponse
import ru.liljvrn.biblimbooker.support.reflection.ManagementApi

@RestController
@RequestMapping("/api/v1/management/book")
@ManagementApi
class BookManagementController(
    private val bookService: BookService
) {

    @PostMapping("/{authorId}")
    fun addBook(@PathVariable authorId: Long, @ModelAttribute request: AddBookRequest): BookResponse =
        bookService.addBook(authorId, request.toDto()).toResponse()
}
