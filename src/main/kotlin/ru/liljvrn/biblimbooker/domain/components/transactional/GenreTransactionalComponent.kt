package ru.liljvrn.biblimbooker.domain.components.transactional

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.liljvrn.biblimbooker.domain.models.dto.AddGenre
import ru.liljvrn.biblimbooker.domain.models.entities.GenreEntity
import ru.liljvrn.biblimbooker.domain.repositories.GenreRepository

@Component
class GenreTransactionalComponent(
    private val genreRepository: GenreRepository
) {

    @Transactional(readOnly = true)
    fun findGenres(): List<GenreEntity> = genreRepository.findAll().toList()

    @Transactional
    fun addGenre(addGenre: AddGenre) =
        genreRepository.save(
            GenreEntity(
                addGenre.genre
            )
        )
}
