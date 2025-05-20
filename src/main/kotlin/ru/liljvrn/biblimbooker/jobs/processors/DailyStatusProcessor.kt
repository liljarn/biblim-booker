package ru.liljvrn.biblimbooker.jobs.processors

import ru.liljvrn.biblimbooker.domain.models.types.DailyJobType

interface DailyStatusProcessor {
    val event: DailyJobType

    fun process()
}
