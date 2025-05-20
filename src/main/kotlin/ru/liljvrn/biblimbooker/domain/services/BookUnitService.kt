package ru.liljvrn.biblimbooker.domain.services

import org.springframework.stereotype.Service
import ru.liljvrn.biblimbooker.domain.clients.GandalfClient
import ru.liljvrn.biblimbooker.domain.components.transactional.BookUnitTransactionalComponent
import ru.liljvrn.biblimbooker.domain.models.dto.BookUnitDetailed

@Service
class BookUnitService(
    private val gandalfClient: GandalfClient,
    private val bookUnitComponent: BookUnitTransactionalComponent
) {

    fun getBookUnits(bookId: Long): List<BookUnitDetailed> {
        val units = bookUnitComponent.findBookUnits(bookId)
        return units.map {
            val userData = it.userId?.let { id -> gandalfClient.getUserById(id) }
            BookUnitDetailed(it, userData)
        }
    }
}
