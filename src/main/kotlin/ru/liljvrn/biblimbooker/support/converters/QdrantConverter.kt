package ru.liljvrn.biblimbooker.support.converters

import io.qdrant.client.grpc.Points.ScoredPoint

object QdrantConverter {
    fun createMetadata(point: ScoredPoint): MutableMap<String, Any> {
        return mutableMapOf<String, Any>().apply {
            put("distance", 1.0f - point.score)
            point.payloadMap.forEach { (key, value) ->
                put(key, value.stringValue)
            }
        }
    }
}
