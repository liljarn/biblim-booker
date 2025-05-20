package ru.liljvrn.biblimbooker.domain.repositories

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.liljvrn.biblimbooker.domain.models.entities.AuthorEntity

@Repository
interface AuthorRepository : CrudRepository<AuthorEntity, Long> {
    fun findAll(page: Pageable): Page<AuthorEntity>
}
