package ru.liljvrn.biblimbooker.infrastructure.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType

@ConfigurationProperties(prefix = "qdrant")
class QdrantProperties {
    lateinit var collections: Map<EmbeddingType, String>
}
