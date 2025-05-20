package ru.liljvrn.biblimbooker.api.rest.v1.management

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.api.rest.json.response.RentResponse
import ru.liljvrn.biblimbooker.domain.services.RentService
import ru.liljvrn.biblimbooker.support.mappers.toResponse
import ru.liljvrn.biblimbooker.support.reflection.ManagementApi
import java.util.*

@RestController
@RequestMapping("/api/v1/management/rent")
@ManagementApi
class RentManagementController(
    private val rentService: RentService,
) {

    @PostMapping("/{bookUnitId}")
    fun acceptClientRent(@PathVariable bookUnitId: String): RentResponse =
        rentService.acceptClientRent(UUID.fromString(bookUnitId)).toResponse()

    @DeleteMapping("/{bookUnitId}")
    fun endClientRent(@PathVariable bookUnitId: String): RentResponse =
        rentService.endClientRent(UUID.fromString(bookUnitId)).toResponse()
}
