package ru.liljvrn.biblimbooker.domain.components.async

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.domain.clients.EmbeddingClient
import ru.liljvrn.biblimbooker.domain.clients.RohanClient
import ru.liljvrn.biblimbooker.domain.models.dto.Book
import ru.liljvrn.biblimbooker.domain.models.dto.BookEmbeddingModel
import ru.liljvrn.biblimbooker.domain.models.dto.PdfBookRequest
import ru.liljvrn.biblimbooker.domain.models.dto.ReportRequest
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType
import java.io.InputStream
import java.util.*

@Component
class BookAsyncComponent(
    private val rohanClient: RohanClient,
    private val embeddingClient: EmbeddingClient,
) {

    @Async
    fun addBookVector(model: BookEmbeddingModel) {
        embeddingClient.addVector(EmbeddingType.BOOK, model)
    }

    @Async
    fun postReport(book: Book, copies: List<UUID>) {
        rohanClient.createReport(
            ReportRequest(
                bookName = book.bookName,
                authorName = book.authorName,
                releaseYear = book.releaseYear,
                ageLimit = book.ageLimit,
                description = book.description,
                downloadable = book.downloadable,
                genres = book.genres.map { it.genreName },
                copiesIds = copies
            )
        )
    }

    @Async
    fun postPdfBook(bookId: Long, book: InputStream) {
        rohanClient.sendPdfBook(
            PdfBookRequest(
                bookId = bookId,
                book = book,
            )
        )
    }
}
