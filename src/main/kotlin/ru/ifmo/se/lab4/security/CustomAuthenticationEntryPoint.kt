package ru.ifmo.se.lab4.security

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import ru.ifmo.se.lab4.presentation.scheme.ResponseScheme
import ru.ifmo.se.lab4.presentation.scheme.ResponseStatus
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = "application/json"
        response.status = HttpStatus.UNAUTHORIZED.value();
        val objectMapper = ObjectMapper()
        val errorResponse = ResponseScheme<Nothing>(
            "Unauthorized: ${authException.message}",
            ResponseStatus.AUTH_ERROR,
        )
        objectMapper.writeValue(response.outputStream, errorResponse)
    }
}
