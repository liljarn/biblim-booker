package ru.liljvrn.biblimbooker.api.rest.v1.management

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.api.rest.json.request.AddGenreRequest
import ru.liljvrn.biblimbooker.api.rest.json.response.GenreResponse
import ru.liljvrn.biblimbooker.domain.services.GenreService
import ru.liljvrn.biblimbooker.support.mappers.toDto
import ru.liljvrn.biblimbooker.support.mappers.toResponse
import ru.liljvrn.biblimbooker.support.reflection.ManagementApi

@RestController
@RequestMapping("/api/v1/management/genre")
@ManagementApi
class GenreManagementController(
    private val genreService: GenreService
) {

    @PostMapping
    fun addNewGenre(@RequestBody request: AddGenreRequest): GenreResponse =
        genreService.addGenre(request.toDto()).toResponse()
}
