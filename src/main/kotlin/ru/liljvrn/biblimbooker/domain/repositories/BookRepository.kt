package ru.liljvrn.biblimbooker.domain.repositories

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.liljvrn.biblimbooker.domain.models.dto.Book
import ru.liljvrn.biblimbooker.domain.models.dto.RentedBook
import ru.liljvrn.biblimbooker.domain.models.entities.BookEntity
import java.util.*

@Repository
interface BookRepository : CrudRepository<BookEntity, Long> {

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.downloadable,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM books b
        JOIN authors a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genres g ON g.genre_id = bg.genre_id
        WHERE b.book_id = :id
        GROUP BY b.book_id, a.author_id
        ORDER BY b.book_id;
    """)
    fun findByBookId(@Param("id") bookId: Long): Book?

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.downloadable,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM books b
        JOIN authors a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genres g ON g.genre_id = bg.genre_id
        GROUP BY b.book_id, a.author_id
        ORDER BY b.book_id
        LIMIT :limit OFFSET :offset;
    """)
    fun findPage(
        @Param("limit") limit: Int,
        @Param("offset") offset: Long
    ): List<Book>

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.downloadable,
            rq.deleted_at as read_date,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM books b
        JOIN authors a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genres g ON g.genre_id = bg.genre_id
        JOIN book_rent_queue rq ON b.book_id = rq.book_id
        WHERE rq.user_id = :id
        GROUP BY b.book_id, a.author_id, rq.deleted_at
        ORDER BY b.book_id
        LIMIT :limit OFFSET :offset;
    """)
    fun findPageHistory(
        @Param("id") userId: UUID,
        @Param("limit") limit: Int,
        @Param("offset") offset: Long
    ): List<RentedBook>

    @Query("""
        SELECT COUNT(b.book_id) FROM books b
        JOIN book_rent_queue brq ON b.book_id = brq.book_id
        WHERE brq.user_id = :id
    """)
    fun countHistory(@Param("id") userId: UUID): Long

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.downloadable,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM books b
        JOIN authors a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genres g ON g.genre_id = bg.genre_id
        WHERE b.author_id = :authorId
        GROUP BY b.book_id, a.author_id
        ORDER BY b.book_id
        LIMIT :limit OFFSET :offset;
    """)
    fun findAuthorsPage(
        @Param("limit") limit: Int,
        @Param("offset") offset: Long,
        @Param("authorId") authorId: Long
    ): List<Book>

    fun countByAuthorId(@Param("authorId") authorId: Long): Long

    @Query("""
        SELECT b.book_id, b.book_name, a.author_id, a.author_name,
            a.author_photo_url, b.release_year, b.age_limit, b.description, b.photo_url, b.rating, b.downloadable,
            jsonb_agg(
                jsonb_build_object(
                    'genreId', g.genre_id,
                    'genreName', g.genre_name
                )
            ) AS genres
        FROM books b
        JOIN authors a ON b.author_id = a.author_id
        JOIN book_genre bg ON bg.book_id = b.book_id
        JOIN genres g ON g.genre_id = bg.genre_id
        WHERE bg.genre_id IN (:genres)
        GROUP BY b.book_id, a.author_id
        ORDER BY b.book_id
        LIMIT :limit OFFSET :offset;
    """)
    fun findPageFiltered(
        @Param("limit") limit: Int,
        @Param("offset") offset: Long,
        @Param("genres") genres: List<Int>,
    ): List<Book>

    @Query("""
        SELECT COUNT(DISTINCT b.book_id) FROM books b
        JOIN book_genre bg ON b.book_id = bg.book_id
        WHERE bg.genre_id IN (:genres)
    """)
    fun countFiltered(@Param("genres") genres: List<Int>): Long

    @Modifying
    @Query("""
        INSERT INTO book_genre(book_id, genre_id)
        VALUES (:bookId, unnest(array[:genres]))
    """)
    fun linkWithGenres(bookId: Long, genres: List<Int>)
}
