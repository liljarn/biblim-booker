package ru.liljvrn.biblimbooker.api.rest.v1.client

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.api.rest.json.request.AddCommentRequest
import ru.liljvrn.biblimbooker.api.rest.json.response.CommentResponse
import ru.liljvrn.biblimbooker.domain.services.CommentService
import ru.liljvrn.biblimbooker.support.mappers.toDto
import ru.liljvrn.biblimbooker.support.mappers.toResponse
import ru.liljvrn.biblimbooker.support.security.nullableUser
import ru.liljvrn.biblimbooker.support.security.softUserContext
import ru.liljvrn.biblimbooker.support.security.user
import ru.liljvrn.biblimbooker.support.security.userContext
import java.util.*

@RestController
@RequestMapping("/api/v1/comments")
class CommentController(
    private val commentService: CommentService
) {

    @GetMapping("/{bookId}")
    fun getBookComments(@PathVariable bookId: Long, @RequestParam page: Int) = softUserContext {
        commentService.getBookComments(bookId, page).toResponse(nullableUser?.userId)
    }

    @PostMapping("/{bookId}")
    fun addComment(
        @PathVariable bookId: Long,
        @RequestBody request: AddCommentRequest
    ): CommentResponse = userContext {
        commentService.addComment(bookId, request.toDto()).toResponse(user.userId)
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@PathVariable commentId: String): CommentResponse = userContext {
        commentService.deleteComment(UUID.fromString(commentId)).toResponse(user.userId)
    }
}
