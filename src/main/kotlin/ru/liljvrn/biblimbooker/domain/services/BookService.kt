package ru.liljvrn.biblimbooker.domain.services

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import ru.liljvrn.biblimbooker.domain.clients.EmbeddingClient
import ru.liljvrn.biblimbooker.domain.clients.ImageClient
import ru.liljvrn.biblimbooker.domain.components.async.BookAsyncComponent
import ru.liljvrn.biblimbooker.domain.components.transactional.BookTransactionalComponent
import ru.liljvrn.biblimbooker.domain.components.transactional.BookUnitTransactionalComponent
import ru.liljvrn.biblimbooker.domain.models.dto.AddBook
import ru.liljvrn.biblimbooker.domain.models.dto.Book
import ru.liljvrn.biblimbooker.domain.models.dto.BookDetailed
import ru.liljvrn.biblimbooker.domain.models.dto.page.BookPage
import ru.liljvrn.biblimbooker.domain.models.dto.page.EmbeddingPage
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType
import ru.liljvrn.biblimbooker.domain.models.types.FilterType
import ru.liljvrn.biblimbooker.domain.models.types.ImageType
import ru.liljvrn.biblimbooker.support.mappers.toBookPage
import ru.liljvrn.biblimbooker.support.mappers.toEmbeddingModel
import ru.liljvrn.biblimbooker.support.pageRequest
import ru.liljvrn.biblimbooker.support.security.nullableUser

@Service
class BookService(
    private val genreService: GenreService,
    private val imageClient: ImageClient,
    private val embeddingClient: EmbeddingClient,
    private val bookAsyncComponent: BookAsyncComponent,
    private val bookComponent: BookTransactionalComponent,
    private val bookUnitComponent: BookUnitTransactionalComponent
) {

    fun findBooksPage(page: Int, query: String?, genres: List<Int>?): BookPage = pageRequest(page) { pageParams ->
        query?.let { embeddingSearch(it, pageParams.offset, genres).toBookPage() } ?: defaultSearch(
            pageParams.pageSize,
            pageParams.offset,
            genres
        )
    }

    fun findAuthorsBooks(authorId: Long, page: Int) = pageRequest(page) {
        bookComponent.findAuthorsBooksPage(authorId, it.pageSize, it.offset)
    }

    fun getBook(bookId: Long): Book = bookComponent.findBook(bookId)

    fun getBookInfo(bookId: Long): BookDetailed {
        val userId = nullableUser?.userId
        return bookComponent.findBookDetailed(bookId, userId)
    }

    fun addBook(authorId: Long, request: AddBook): Book {
        val downloadable = request.source != null
        val imageUrl = imageClient.uploadImage(request.photo, request.bookName, ImageType.BOOK)
        val book = bookComponent.addBook(authorId, request, imageUrl, downloadable)
        if (downloadable) {
            bookAsyncComponent.postPdfBook(book.bookId, request.source!!)
        }
        exportBookData(book)
        return book
    }

    fun exportAllBooks() = pageRequest(0) {
        var page: PageRequest = it
        do {
            val bookPage = bookComponent.findBooksPage(page.pageSize, page.offset)
            for (book in bookPage.books) {
                exportBookData(book)
            }
            page = page.next()
        } while (bookPage.books.isNotEmpty())
    }

    private fun embeddingSearch(query: String, offset: Long, genres: List<Int>?): EmbeddingPage =
        if (genres.isNullOrEmpty()) {
            embeddingClient.searchPage(EmbeddingType.BOOK, query, offset)
        } else {
            val filterGenres = genreService.getGenres().filter { it.genreId in genres }.map { it.genreName }
            println(filterGenres)
            embeddingClient.searchPageFiltered(
                EmbeddingType.BOOK,
                query,
                offset,
                FilterType.GENRE,
                *filterGenres.toTypedArray()
            )
        }

    private fun defaultSearch(limit: Int, offset: Long, genres: List<Int>?): BookPage =
        if (genres.isNullOrEmpty()) {
            bookComponent.findBooksPage(limit, offset)
        } else {
            bookComponent.findBooksPageFiltered(limit, offset, genres)
        }

    private fun exportBookData(book: Book) {
        val unitIds = bookUnitComponent.findBookUnits(book.bookId).map { it.bookUnitId }
        bookAsyncComponent.addBookVector(book.toEmbeddingModel())
        bookAsyncComponent.postReport(book, unitIds)
    }
}
