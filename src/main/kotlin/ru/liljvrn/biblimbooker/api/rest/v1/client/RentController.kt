package ru.liljvrn.biblimbooker.api.rest.v1.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.api.rest.json.response.RentedBookPageResponse
import ru.liljvrn.biblimbooker.domain.services.RentService
import ru.liljvrn.biblimbooker.support.mappers.toResponse
import java.util.*

@RestController
@RequestMapping("/api/v1/rent")
class RentController(
    private val rentService: RentService
) {

    @GetMapping("/{userId}/history")
    fun getRentHistory(@RequestParam page: Int, @PathVariable userId: String): RentedBookPageResponse =
        rentService.getClientRentHistory(page, UUID.fromString(userId)).toResponse()
}
