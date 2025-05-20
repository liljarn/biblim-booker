package ru.liljvrn.biblimbooker.support.factories

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import ru.liljvrn.biblimbooker.support.configurations.WebClientBuilders

@Component
class WebClientFactory(
    private val webClientBuilders: WebClientBuilders
) {

    fun createWebClient(name: String): WebClient {
        val webClientBuilder = checkNotNull(webClientBuilders.builders[name]) {
            "WebClient builder $name properties were not found"
        }
        return webClientBuilder.build()
    }
}
