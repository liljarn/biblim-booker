package ru.liljvrn.biblimbooker.domain.models.dto.page

import ru.liljvrn.biblimbooker.domain.models.dto.RentedBook

data class RentedBookPage(
    val total: Long,
    val rentedBooks: List<RentedBook>
)
