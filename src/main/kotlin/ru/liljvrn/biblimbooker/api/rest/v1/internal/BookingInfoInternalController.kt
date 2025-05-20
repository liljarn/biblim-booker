package ru.liljvrn.biblimbooker.api.rest.v1.internal

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.liljvrn.biblimbooker.domain.services.BookingInfoService
import ru.liljvrn.biblimbooker.support.consts.GANDALF
import ru.liljvrn.biblimbooker.support.reflection.InternalApi
import java.util.*

@RestController
@RequestMapping("/api/v1/internal/booking")
class BookingInfoInternalController(
    private val bookingInfoService: BookingInfoService,
) {

    @InternalApi(GANDALF)
    @GetMapping("/{userId}")
    fun getClientBookingInfo(@PathVariable userId: UUID) = bookingInfoService.getClientCurrentBookingInfo(userId)
}
