package ru.liljvrn.biblimbooker.domain.components.transactional

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.liljvrn.biblimbooker.domain.models.dto.AddAuthor
import ru.liljvrn.biblimbooker.domain.models.entities.AuthorEntity
import ru.liljvrn.biblimbooker.domain.repositories.AuthorRepository

@Component
class AuthorTransactionalComponent(
    private val authorRepository: AuthorRepository
) {

    @Transactional(readOnly = true)
    fun findAuthorById(authorId: Long): AuthorEntity? = authorRepository.findByIdOrNull(authorId)

    @Transactional(readOnly = true)
    fun findAuthorsPage(authorPage: Pageable): Page<AuthorEntity> = authorRepository.findAll(authorPage)

    @Transactional
    fun saveAuthor(addAuthor: AddAuthor, imageUrl: String) =
        authorRepository.save(
            AuthorEntity(
                authorName = addAuthor.authorName,
                authorDescription = addAuthor.authorDescription,
                authorPhotoUrl = imageUrl
            )
        )
}
