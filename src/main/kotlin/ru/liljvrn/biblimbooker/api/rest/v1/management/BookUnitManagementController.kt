package ru.liljvrn.biblimbooker.api.rest.v1.management

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.domain.models.dto.BookUnitDetailed
import ru.liljvrn.biblimbooker.domain.services.BookService
import ru.liljvrn.biblimbooker.domain.services.BookUnitService
import ru.liljvrn.biblimbooker.support.reflection.ManagementApi

@RestController
@RequestMapping("/api/v1/management/bookunit")
@ManagementApi
class BookUnitManagementController(
    private val bookService: BookService,
    private val bookUnitService: BookUnitService,
) {

    @GetMapping("/{bookId}")
    fun getBookUnits(@PathVariable bookId: Long): BookUnitDetailedResponse {
        val book = bookService.getBook(bookId)
        val units = bookUnitService.getBookUnits(bookId)

        return BookUnitDetailedResponse(
            authorName = book.authorName,
            bookName = book.bookName,
            bookUnits = units,
        )
    }
}

data class BookUnitDetailedResponse(
    val authorName: String,
    val bookName: String,
    val bookUnits: List<BookUnitDetailed>
)
