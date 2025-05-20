package ru.liljvrn.biblimbooker.domain.components.transactional

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.liljvrn.biblimbooker.domain.models.dto.AddComment
import ru.liljvrn.biblimbooker.domain.models.entities.CommentEntity
import ru.liljvrn.biblimbooker.domain.repositories.CommentRepository
import ru.liljvrn.biblimbooker.support.security.user
import java.util.*

@Component
class CommentTransactionalComponent(
    private val commentRepository: CommentRepository
) {

    @Transactional
    fun saveComment(bookId: Long, request: AddComment): CommentEntity {
        val comment = CommentEntity(
            commentId = UUID.randomUUID(),
            userId = user.userId,
            comment = request.comment,
            rating = request.rating,
            bookId = bookId
        )
        commentRepository.save(comment)
        return comment
    }

    @Transactional
    fun deleteComment(commentId: UUID): CommentEntity {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw RuntimeException()
        commentRepository.deleteById(commentId)

        return comment
    }

    @Transactional(readOnly = true)
    fun findBookComments(page: Pageable, bookId: Long): Page<CommentEntity> =
        commentRepository.findAllByBookId(page, bookId)
}
