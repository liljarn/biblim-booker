package ru.liljvrn.biblimbooker.api.rest.v1.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.api.rest.json.response.BookPageResponse
import ru.liljvrn.biblimbooker.domain.services.BookService
import ru.liljvrn.biblimbooker.support.mappers.toResponse
import ru.liljvrn.biblimbooker.support.security.softUserContext

@RestController
@RequestMapping("/api/v1/book")
class BookController(
    private val bookService: BookService
) {

    @GetMapping("/list")
    fun findPage(
        @RequestParam page: Int = 0,
        @RequestParam query: String?,
        @RequestParam genres: List<Int>?
    ): BookPageResponse = bookService.findBooksPage(page, query, genres).toResponse()

    @GetMapping("/author/{authorId}")
    fun findAuthorsBooks(
        @PathVariable authorId: Long,
        @RequestParam page: Int = 0
    ): BookPageResponse = bookService.findAuthorsBooks(authorId, page).toResponse()

    @GetMapping("/info/{bookId}")
    fun getBookInfo(@PathVariable bookId: Long) = softUserContext {
        bookService.getBookInfo(bookId)
    }
}
