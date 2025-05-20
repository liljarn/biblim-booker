package ru.liljvrn.biblimbooker.domain.services

import org.springframework.stereotype.Service
import ru.liljvrn.biblimbooker.domain.clients.GandalfClient
import ru.liljvrn.biblimbooker.domain.components.transactional.CommentTransactionalComponent
import ru.liljvrn.biblimbooker.domain.models.dto.AddComment
import ru.liljvrn.biblimbooker.domain.models.dto.Comment
import ru.liljvrn.biblimbooker.domain.models.dto.page.CommentPage
import ru.liljvrn.biblimbooker.support.mappers.toDto
import ru.liljvrn.biblimbooker.support.mappers.toPage
import ru.liljvrn.biblimbooker.support.pageRequest
import ru.liljvrn.biblimbooker.support.security.user
import java.util.*

@Service
class CommentService(
    private val commentComponent: CommentTransactionalComponent,
    private val gandalfClient: GandalfClient
) {

    fun addComment(bookId: Long, request: AddComment): Comment {
        val entity = commentComponent.saveComment(bookId, request)
        val userData = gandalfClient.getUserById(user.userId)

        return entity.toDto(userData)
    }

    fun deleteComment(commentId: UUID): Comment {
        val entity = commentComponent.deleteComment(commentId)
        val userData = gandalfClient.getUserById(entity.userId)

        return entity.toDto(userData)
    }

    fun getBookComments(bookId: Long, page: Int): CommentPage = pageRequest(page) {
        val commentPage = commentComponent.findBookComments(it, bookId)
        val usersData = commentPage.content.map { comment -> gandalfClient.getUserById(comment.userId) }
        commentPage.toPage(usersData)
    }
}
