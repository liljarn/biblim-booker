package ru.liljvrn.biblimbooker.infrastructure.qdrant

import io.qdrant.client.QdrantClient
import io.qdrant.client.WithPayloadSelectorFactory.enable
import io.qdrant.client.grpc.Points
import io.qdrant.client.grpc.Points.ScoredPoint
import io.qdrant.client.grpc.Points.SearchPoints
import org.springframework.ai.document.Document
import org.springframework.ai.document.DocumentMetadata
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.model.EmbeddingUtils
import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.domain.clients.EmbeddingClient
import ru.liljvrn.biblimbooker.domain.models.dto.EmbeddingDataModel
import ru.liljvrn.biblimbooker.domain.models.dto.page.EmbeddingPage
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType
import ru.liljvrn.biblimbooker.domain.models.types.FilterType
import ru.liljvrn.biblimbooker.infrastructure.qdrant.processors.QdrantEmbeddingProcessor
import ru.liljvrn.biblimbooker.support.JsonUtils
import ru.liljvrn.biblimbooker.support.PAGE_SIZE
import ru.liljvrn.biblimbooker.support.converters.QdrantConverter

@Component
class QdrantEmbeddingClient(
    private val qdrantClient: QdrantClient,
    private val embeddingModel: EmbeddingModel,
    processors: List<QdrantEmbeddingProcessor>
) : EmbeddingClient {

    private val processorsMap = processors.associateBy { it.type }

    override fun searchPage(type: EmbeddingType, query: String, offset: Long): EmbeddingPage {
        val filter = Points.Filter.getDefaultInstance()
        val queryEmbedding = embeddingModel.embed(query)
        val searchPoints = SearchPoints.newBuilder().setCollectionName(type.value).setLimit(PAGE_SIZE.toLong())
            .setWithPayload(enable(true))
            .addAllVector(EmbeddingUtils.toList(queryEmbedding)).setFilter(filter)
            .setOffset(offset)
            .build()
        val queryResponse = qdrantClient.searchAsync(searchPoints)
        val count = qdrantClient.countAsync(type.value)

        return EmbeddingPage(
            total = count.get(),
            models = queryResponse.get().map {
                toDocument(it).metadata.let { meta -> JsonUtils.convertObject(meta, type.modelType) }
            }
        )
    }

    override fun addVector(type: EmbeddingType, model: EmbeddingDataModel) {
        processorsMap[type]!!.addVector(model)
    }

    private fun toDocument(point: ScoredPoint): Document {
        try {
            val id = point.id.uuid
            val metadata = QdrantConverter.createMetadata(point)
            metadata[DocumentMetadata.DISTANCE.value()] = 1.0f - point.score
            val content = metadata.remove("doc_content") as String?
            return Document.builder().id(id).text(content).metadata(metadata).score(point.score.toDouble()).build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
