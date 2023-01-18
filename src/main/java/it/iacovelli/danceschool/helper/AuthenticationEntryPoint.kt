package it.iacovelli.danceschool.helper

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class AuthenticationEntryPoint : BasicAuthenticationEntryPoint() {

    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest?, response: HttpServletResponse, authEx: AuthenticationException) {
        response.addHeader("WWW-Authenticate", "Basic realm=$realmName")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val writer = response.writer
        writer.println("HTTP Status 401 - " + authEx.message)
    }

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        realmName = "DeveloperStack"
        super.afterPropertiesSet()
    }

}
