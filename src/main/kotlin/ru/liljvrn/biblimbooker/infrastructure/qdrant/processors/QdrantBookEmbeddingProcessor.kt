package ru.liljvrn.biblimbooker.infrastructure.qdrant.processors

import org.springframework.ai.document.Document
import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.domain.models.dto.BookEmbeddingModel
import ru.liljvrn.biblimbooker.domain.models.dto.EmbeddingDataModel
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType
import ru.liljvrn.biblimbooker.infrastructure.configurations.QdrantVectorStores

@Component
class QdrantBookEmbeddingProcessor(
    qdrantVectorStores: QdrantVectorStores
) : QdrantEmbeddingProcessor {

    final override val type = EmbeddingType.BOOK
    private val vectorStore = qdrantVectorStores.vectorStores[type]!!

    override fun addVector(model: EmbeddingDataModel) {
        require(model is BookEmbeddingModel) { "Expected BookEmbeddingModel, got ${model::class.simpleName}" }
        vectorStore.add(
            listOf(
                Document(
                    "${model.bookName} ${model.authorName}",
                    mapOf(
                        "bookId" to model.bookId,
                        "bookName" to model.bookName,
                        "authorId" to model.authorId,
                        "authorName" to model.authorName,
                        "authorPhotoUrl" to model.authorPhotoUrl,
                        "releaseYear" to model.releaseYear,
                        "ageLimit" to model.ageLimit,
                        "description" to model.description,
                        "photoUrl" to model.photoUrl,
                        "rating" to model.rating,
                        "downloadable" to model.downloadable,
                        "genres" to model.genres,
                    )
                )
            )
        )
    }
}
