package ru.liljvrn.biblimbooker.domain.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.liljvrn.biblimbooker.domain.models.entities.CommentEntity
import java.util.*

@Repository
interface CommentRepository : CrudRepository<CommentEntity, UUID> {

    fun findAllByBookId(page: Pageable, bookId: Long): Page<CommentEntity>

    @Modifying
    @Query("""
        INSERT INTO comments (comment_id, user_id, comment, rating, book_id)
        VALUES (:#{#comment.commentId}, :#{#comment.userId}, :#{#comment.comment}, :#{#comment.rating}, :#{#comment.bookId})
    """)
    fun save(@Param("comment") commentEntity: CommentEntity)
}
