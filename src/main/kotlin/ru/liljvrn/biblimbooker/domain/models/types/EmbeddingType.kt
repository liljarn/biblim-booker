package ru.liljvrn.biblimbooker.domain.models.types

import ru.liljvrn.biblimbooker.domain.models.dto.AuthorEmbeddingModel
import ru.liljvrn.biblimbooker.domain.models.dto.BookEmbeddingModel
import ru.liljvrn.biblimbooker.domain.models.dto.EmbeddingDataModel
import kotlin.reflect.KClass

enum class EmbeddingType(val value: String, val modelType: KClass<out EmbeddingDataModel>) {
    AUTHOR("authors", AuthorEmbeddingModel::class),
    BOOK("books", BookEmbeddingModel::class),
}
