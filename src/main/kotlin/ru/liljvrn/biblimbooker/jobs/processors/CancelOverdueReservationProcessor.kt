package ru.liljvrn.biblimbooker.jobs.processors

import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.domain.models.types.DailyJobType
import ru.liljvrn.biblimbooker.domain.services.ReservationService

@Component
class CancelOverdueReservationProcessor(
    private val reservationService: ReservationService,
) : DailyStatusProcessor {

    override val event = DailyJobType.CANCEL_OVERDUE_RESERVATION

    override fun process() = reservationService.removeOverdueReservation()
}
