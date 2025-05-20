package ru.liljvrn.biblimbooker.infrastructure.qdrant.processors

import ru.liljvrn.biblimbooker.domain.models.dto.EmbeddingDataModel
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType

interface QdrantEmbeddingProcessor {
    val type: EmbeddingType

    fun addVector(model: EmbeddingDataModel)
}
