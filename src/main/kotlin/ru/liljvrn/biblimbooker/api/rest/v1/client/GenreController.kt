package ru.liljvrn.biblimbooker.api.rest.v1.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.domain.services.GenreService
import ru.liljvrn.biblimbooker.support.mappers.toResponse

@RestController
@RequestMapping("/api/v1/genre")
class GenreController(
    private val genreService: GenreService,
) {

    @GetMapping
    fun getGenres() = genreService.getGenres().map { it.toResponse() }
}
