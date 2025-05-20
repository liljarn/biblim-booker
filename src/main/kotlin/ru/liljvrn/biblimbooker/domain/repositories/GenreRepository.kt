package ru.liljvrn.biblimbooker.domain.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.liljvrn.biblimbooker.domain.models.entities.GenreEntity

@Repository
interface GenreRepository : CrudRepository<GenreEntity, Int>
