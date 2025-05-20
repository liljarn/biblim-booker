package ru.liljvrn.biblimbooker.infrastructure.configurations

import io.qdrant.client.QdrantClient
import io.qdrant.client.grpc.Collections
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType
import ru.liljvrn.biblimbooker.infrastructure.properties.QdrantProperties

@Configuration
class QdrantConfiguration(
    private val qdrantClient: QdrantClient,
) {

    @Bean
    fun qdrantVectorStores(
        qdrantProperties: QdrantProperties,
        embeddingModel: EmbeddingModel
    ): QdrantVectorStores {
        val vectorStores = mutableMapOf<EmbeddingType, VectorStore>()
        qdrantProperties.collections.forEach { (key, collection) ->
            initCollection(collection)
            QdrantVectorStore.builder(qdrantClient, embeddingModel).collectionName(collection).build().let {
                vectorStores[key] = it
            }
        }
        return QdrantVectorStores(vectorStores)
    }

    private fun initCollection(collectionName: String) {
        val exists = qdrantClient.collectionExistsAsync(collectionName).get()

        if (!exists) {
            qdrantClient.createCollectionAsync(
                Collections.CreateCollection.newBuilder()
                    .setCollectionName(collectionName)
                    .setVectorsConfig(
                        Collections.VectorsConfig.newBuilder()
                            .setParams(
                                Collections.VectorParams.newBuilder()
                                    .setSize(1024)
                                    .setDistance(Collections.Distance.Cosine)
                                    .build()
                            )
                    )
                    .build()
            ).get()
        }
    }
}

data class QdrantVectorStores(
    val vectorStores: Map<EmbeddingType, VectorStore>
)
