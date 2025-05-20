package ru.liljvrn.biblimbooker.jobs.processors

import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.domain.models.types.DailyJobType
import ru.liljvrn.biblimbooker.domain.services.RentService

@Component
class NotifyEndRentProcessor(
    private val rentService: RentService
) : DailyStatusProcessor {

    override val event = DailyJobType.NOTIFY_END_RENT

    override fun process() {
        rentService.notifyEndRent()
    }
}
