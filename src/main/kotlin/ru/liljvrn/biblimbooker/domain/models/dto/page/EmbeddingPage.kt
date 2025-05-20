package ru.liljvrn.biblimbooker.domain.models.dto.page

import ru.liljvrn.biblimbooker.domain.models.dto.EmbeddingDataModel

data class EmbeddingPage(
    val total: Long,
    val models: List<EmbeddingDataModel>
)
