package ru.liljvrn.biblimbooker.domain.components.transactional

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.liljvrn.biblimbooker.domain.models.dto.AddBook
import ru.liljvrn.biblimbooker.domain.models.dto.Book
import ru.liljvrn.biblimbooker.domain.models.dto.BookDetailed
import ru.liljvrn.biblimbooker.domain.models.dto.page.BookPage
import ru.liljvrn.biblimbooker.domain.models.entities.BookEntity
import ru.liljvrn.biblimbooker.domain.models.entities.BookUnitEntity
import ru.liljvrn.biblimbooker.domain.models.types.BookStatus
import ru.liljvrn.biblimbooker.domain.repositories.BookRepository
import ru.liljvrn.biblimbooker.domain.repositories.BookUnitRepository
import ru.liljvrn.biblimbooker.domain.repositories.RentRepository
import ru.liljvrn.biblimbooker.domain.repositories.ReservationRepository
import java.util.*

@Component
class BookTransactionalComponent(
    private val bookRepository: BookRepository,
    private val bookUnitRepository: BookUnitRepository,
    private val rentRepository: RentRepository,
    private val reservationRepository: ReservationRepository,
) {

    @Transactional(readOnly = true)
    fun findBooksPage(limit: Int, offset: Long): BookPage {
        val booksPage = bookRepository.findPage(limit, offset)
        val total = bookRepository.count()

        return BookPage(total, booksPage)
    }

    @Transactional(readOnly = true)
    fun findBook(bookId: Long): Book {
        return bookRepository.findByBookId(bookId)!!
    }

    @Transactional(readOnly = true)
    fun findBooksPageFiltered(limit: Int, offset: Long, genres: List<Int>): BookPage {
        val booksPage = bookRepository.findPageFiltered(limit, offset, genres)
        val total = bookRepository.countFiltered(genres)

        return BookPage(total, booksPage)
    }

    @Transactional(readOnly = true)
    fun findAuthorsBooksPage(authorId: Long, limit: Int, offset: Long): BookPage {
        val booksPage = bookRepository.findAuthorsPage(limit, offset, authorId)
        val total = bookRepository.countByAuthorId(authorId)

        return BookPage(total, booksPage)
    }

    @Transactional(readOnly = true)
    fun findBookDetailed(bookId: Long, userId: UUID?): BookDetailed {
        val book = requireNotNull(bookRepository.findByBookId(bookId))
        val bookStatus = getBookStatus(bookId, userId)

        return BookDetailed(
            book = book,
            status = bookStatus
        )
    }

    @Transactional
    fun addBook(authorId: Long, request: AddBook, imageUrl: String, downloadable: Boolean): Book {
        val entity = bookRepository.save(
            BookEntity(
                bookName = request.bookName,
                authorId = authorId,
                releaseYear = request.releaseYear,
                ageLimit = request.ageLimit,
                description = request.description,
                downloadable = downloadable,
                photoUrl = imageUrl
            )
        )
        bookRepository.linkWithGenres(entity.bookId!!, request.genres)
        saveUnits(request.units, entity.bookId!!)
        return checkNotNull(bookRepository.findByBookId(entity.bookId!!))
    }

    private fun getBookStatus(bookId: Long, userId: UUID?): BookStatus {
        if (userId == null) {
            return BookStatus.NOT_AVAILABLE
        }

        reservationRepository.findByUserId(userId)?.let {
            if (it.bookId == bookId) {
                return BookStatus.BOOKED
            } else {
                return BookStatus.NOT_AVAILABLE
            }
        }

        rentRepository.findByUserIdAndDeletedAtIsNull(userId)?.let {
            if (it.bookId == bookId) {
                return BookStatus.READING
            } else {
                return BookStatus.NOT_AVAILABLE
            }
        }

        val bookUnits = bookUnitRepository.findByBookId(bookId)

        return if (bookUnits.any { it.status == BookStatus.AVAILABLE }) {
            BookStatus.AVAILABLE
        } else {
            BookStatus.NOT_AVAILABLE
        }
    }

    private fun saveUnits(unitsCount: Short, bookId: Long) {
        for (i in 0 until unitsCount) {
            bookUnitRepository.addUnit(
                BookUnitEntity(
                    bookUnitId = UUID.randomUUID(),
                    bookId = bookId
                )
            )
        }
    }
}
