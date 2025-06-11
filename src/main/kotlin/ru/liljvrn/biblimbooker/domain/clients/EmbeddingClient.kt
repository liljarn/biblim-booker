package ru.liljvrn.biblimbooker.domain.clients

import ru.liljvrn.biblimbooker.domain.models.dto.EmbeddingDataModel
import ru.liljvrn.biblimbooker.domain.models.dto.page.EmbeddingPage
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType

interface EmbeddingClient {

    fun searchPage(type: EmbeddingType, query: String, offset: Long = 0): EmbeddingPage

    fun addVector(type: EmbeddingType, model: EmbeddingDataModel)
}
