package ru.liljvrn.biblimbooker.domain.clients

import ru.liljvrn.biblimbooker.domain.models.dto.EmbeddingDataModel
import ru.liljvrn.biblimbooker.domain.models.dto.page.EmbeddingPage
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType
import ru.liljvrn.biblimbooker.domain.models.types.FilterType

interface EmbeddingClient {

    fun searchPage(type: EmbeddingType, query: String, offset: Long = 0): EmbeddingPage

    fun searchPageFiltered(
        type: EmbeddingType,
        query: String,
        offset: Long = 0,
        filterType: FilterType,
        vararg values: String
    ): EmbeddingPage

    fun addVector(type: EmbeddingType, model: EmbeddingDataModel)
}
