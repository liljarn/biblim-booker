package ru.liljvrn.biblimbooker.support.reflection

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component
import ru.liljvrn.biblimbooker.support.properties.InternalApiProperties
import ru.liljvrn.biblimbooker.support.properties.ManagementApiProperties

@Component
class ValidationHandler(
    private val managementApiProperties: ManagementApiProperties,
    private val internalApiProperties: InternalApiProperties
) {

    fun validateHeader(request: HttpServletRequest): Boolean {
        val managementRequestKey = request.getHeader(managementApiProperties.header)
        return managementRequestKey == managementApiProperties.key
    }

    fun validateInternalHeader(request: HttpServletRequest, internalApi: InternalApi): Boolean {
        val internalHeader = request.getHeader(internalApiProperties.header)
        return internalApi.service.any { service ->
            internalApiProperties.services[service] == internalHeader
        }
    }
}
