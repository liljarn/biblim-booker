package ru.liljvrn.biblimbooker.jobs

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.jobs.processors.DailyStatusProcessor

@Component
class DailyStatusCheckJob(
    @Value("\${jobs.reservationCheck.enabled}")
    private val jobEnabled: Boolean,
    private val handlers: List<DailyStatusProcessor>
) {

    @Scheduled(cron = "\${jobs.reservationCheck.cron}")
    fun checkBookingStatus() {
        if (jobEnabled) {
            handlers.forEach { it.process() }
        }
    }
}
