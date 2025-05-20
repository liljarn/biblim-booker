package ru.liljvrn.biblimbooker.support.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import ru.liljvrn.biblimbooker.support.security.AuthenticationEnrichmentInterceptor

@Configuration
class AuthorizationConfiguration(
    private val authenticationEnrichmentInterceptor: AuthenticationEnrichmentInterceptor
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(authenticationEnrichmentInterceptor)
        super.addInterceptors(registry)
    }
}
