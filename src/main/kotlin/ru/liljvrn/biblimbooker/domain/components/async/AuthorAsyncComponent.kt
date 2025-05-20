package ru.liljvrn.biblimbooker.domain.components.async

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.domain.clients.EmbeddingClient
import ru.liljvrn.biblimbooker.domain.models.dto.AuthorEmbeddingModel
import ru.liljvrn.biblimbooker.domain.models.types.EmbeddingType

@Component
class AuthorAsyncComponent(
    private val embeddingClient: EmbeddingClient
) {
    @Async
    fun addAuthorVector(model: AuthorEmbeddingModel) {
        embeddingClient.addVector(EmbeddingType.AUTHOR, model)
    }
}
