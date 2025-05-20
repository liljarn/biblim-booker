package ru.liljvrn.biblimbooker.api.rest.v1.client

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.api.rest.json.response.ReservationResponse
import ru.liljvrn.biblimbooker.domain.services.ReservationService
import ru.liljvrn.biblimbooker.support.mappers.toResponse
import ru.liljvrn.biblimbooker.support.security.userContext

@RestController
@RequestMapping("/api/v1/reservation")
class ReservationController(
    private val reservationService: ReservationService
) {
    @PostMapping("/{bookId}")
    fun reserveBook(@PathVariable bookId: Long): ReservationResponse = userContext {
        reservationService.reserveBook(bookId).toResponse()
    }

    @DeleteMapping
    fun cancelBookReservation(): ReservationResponse = userContext {
        reservationService.removeClientReservation().toResponse()
    }
}
