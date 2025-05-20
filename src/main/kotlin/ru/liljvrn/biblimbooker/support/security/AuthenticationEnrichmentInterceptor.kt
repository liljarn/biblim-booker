package ru.liljvrn.biblimbooker.support.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import ru.liljvrn.biblimbooker.domain.clients.GandalfClient
import ru.liljvrn.biblimbooker.support.security.SecurityContextHolder.createContext

@Component
class AuthenticationEnrichmentInterceptor(
    private val gandalfClient: GandalfClient
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean =
        runCatching {
            if (request.getHeader("Authorization") != null) {
                val token = request.getHeader("Authorization").replace("Bearer ", "")
                createContext(gandalfClient.getUserByToken(token), token)
            }
            return super.preHandle(request, response, handler)
        }.getOrElse {
            response.status = HttpStatus.UNAUTHORIZED.value()
            false
        }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        SecurityContextHolder.securityContext.remove()
    }
}
