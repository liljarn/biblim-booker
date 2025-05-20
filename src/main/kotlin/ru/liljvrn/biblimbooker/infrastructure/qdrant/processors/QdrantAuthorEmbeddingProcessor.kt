package ru.liljvrn.biblimbooker.infrastructure.qdrant.processors

import org.springframework.ai.document.Document
import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.domain.models.dto.AuthorEmbeddingModel
import ru.liljvrn.biblimbooker.domain.models.dto.EmbeddingDataModel
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType
import ru.liljvrn.biblimbooker.infrastructure.configurations.QdrantVectorStores

@Component
class QdrantAuthorEmbeddingProcessor(
    qdrantVectorStores: QdrantVectorStores
) : QdrantEmbeddingProcessor {
    final override val type = EmbeddingType.AUTHOR
    private val vectorStore = qdrantVectorStores.vectorStores[type]!!

    override fun addVector(model: EmbeddingDataModel) {
        require(model is AuthorEmbeddingModel) { "Expected AuthorEmbeddingModel, got ${model::class.simpleName}" }
        vectorStore.add(
            listOf(
                Document(
                    model.authorName,
                    mapOf(
                        "authorId" to model.authorId,
                        "authorName" to model.authorName,
                        "authorDescription" to model.authorDescription,
                        "authorPhotoUrl" to model.authorPhotoUrl
                    )
                )
            )
        )
    }
}
