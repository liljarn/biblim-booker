package ru.liljvrn.biblimbooker.domain.services

import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.liljvrn.biblimbooker.domain.clients.EmbeddingClient
import ru.liljvrn.biblimbooker.domain.clients.ImageClient
import ru.liljvrn.biblimbooker.domain.components.async.AuthorAsyncComponent
import ru.liljvrn.biblimbooker.domain.components.transactional.AuthorTransactionalComponent
import ru.liljvrn.biblimbooker.domain.models.dto.AddAuthor
import ru.liljvrn.biblimbooker.domain.models.dto.Author
import ru.liljvrn.biblimbooker.domain.models.dto.page.AuthorPage
import ru.liljvrn.biblimbooker.domain.models.dto.page.EmbeddingPage
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType
import ru.liljvrn.biblimbooker.domain.models.types.ImageType
import ru.liljvrn.biblimbooker.support.PAGE_SIZE
import ru.liljvrn.biblimbooker.support.mappers.toAuthorPage
import ru.liljvrn.biblimbooker.support.mappers.toDto
import ru.liljvrn.biblimbooker.support.mappers.toEmbeddingModel
import ru.liljvrn.biblimbooker.support.mappers.toPage
import ru.liljvrn.biblimbooker.support.pageRequest

@Service
class AuthorService(
    private val imageClient: ImageClient,
    private val embeddingClient: EmbeddingClient,
    private val authorAsyncComponent: AuthorAsyncComponent,
    private val authorComponent: AuthorTransactionalComponent
) {

    fun getAuthors(page: Int, query: String?): AuthorPage = pageRequest(page) {
        query?.let { embeddingSearch(query, page.toLong()).toAuthorPage() } ?: authorComponent.findAuthorsPage(it)
            .toPage()
    }

    fun getAuthorInfo(authorId: Long): Author = authorComponent.findAuthorById(authorId)?.toDto()
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")

    fun addAuthor(request: AddAuthor): Author {
        val imageUrl = imageClient.uploadImage(request.authorPhoto, request.authorName, ImageType.AUTHOR)
        val author = authorComponent.saveAuthor(request, imageUrl).toDto()
        return author
    }

    fun exportAllAuthors() = pageRequest(0) {
        var page: PageRequest = it
        do {
            val authors = authorComponent.findAuthorsPage(page)
            authors.content.forEach { author -> exportAuthor(author.toDto()) }
            page = page.next()
        } while (!authors.isEmpty)
    }

    private fun exportAuthor(author: Author) {
        authorAsyncComponent.addAuthorVector(author.toEmbeddingModel())
    }

    private fun embeddingSearch(query: String, page: Long): EmbeddingPage =
        embeddingClient.searchPage(EmbeddingType.AUTHOR, query, page * PAGE_SIZE)
}
