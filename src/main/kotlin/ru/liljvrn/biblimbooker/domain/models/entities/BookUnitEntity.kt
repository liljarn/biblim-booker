package ru.liljvrn.biblimbooker.domain.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.liljvrn.biblimbooker.domain.models.types.BookStatus
import java.util.UUID

@Table(name = "book_units")
class BookUnitEntity(
    @Id
    val bookUnitId: UUID,
    val bookId: Long
) {
    var status: BookStatus = BookStatus.AVAILABLE
}
