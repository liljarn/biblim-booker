package ru.liljvrn.biblimbooker.domain.services

import org.springframework.stereotype.Service
import ru.liljvrn.biblimbooker.domain.components.transactional.GenreTransactionalComponent
import ru.liljvrn.biblimbooker.domain.models.dto.AddGenre
import ru.liljvrn.biblimbooker.domain.models.dto.Genre
import ru.liljvrn.biblimbooker.support.mappers.toDto

@Service
class GenreService(
    private val genreComponent: GenreTransactionalComponent
) {

    fun getGenres(): List<Genre> = genreComponent.findGenres().map { it.toDto() }

    fun addGenre(request: AddGenre): Genre = genreComponent.addGenre(request).toDto()
}