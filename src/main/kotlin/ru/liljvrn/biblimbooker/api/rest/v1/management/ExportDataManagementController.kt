package ru.liljvrn.biblimbooker.api.rest.v1.management

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.domain.services.AuthorService
import ru.liljvrn.biblimbooker.domain.services.BookService
import ru.liljvrn.biblimbooker.support.reflection.ManagementApi

@RestController
@RequestMapping("/api/v1/management/export")
@ManagementApi
class ExportDataManagementController(
    private val authorService: AuthorService,
    private val bookService: BookService,
) {

    @PostMapping
    fun export() {
        authorService.exportAllAuthors()
        bookService.exportAllBooks()
    }
}
