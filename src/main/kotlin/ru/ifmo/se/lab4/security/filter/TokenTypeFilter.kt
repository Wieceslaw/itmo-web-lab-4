package ru.ifmo.se.lab4.security.filter

import lombok.AllArgsConstructor
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.server.resource.BearerTokenErrors
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.filter.GenericFilterBean
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse


@AllArgsConstructor
class TokenTypeFilter(private val type: TokenType) : GenericFilterBean() {
    enum class TokenType {
        ACCESS, REFRESH;

        override fun toString(): String {
            return super.toString().lowercase(Locale.getDefault())
        }
    }

    override fun doFilter(
        servletRequest: ServletRequest,
        servletResponse: ServletResponse,
        filterChain: FilterChain
    ) {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication is JwtAuthenticationToken) {
            val token = authentication.token
            if (!token.hasClaim("type")) {
                val error = BearerTokenErrors
                    .invalidRequest("No type claim in token payload")
                throw OAuth2AuthenticationException(error)
            } else if (token.getClaim<Any>("type") != type.toString()) {
                val error = BearerTokenErrors
                    .invalidRequest("Wrong type claim in token payload")
                throw OAuth2AuthenticationException(error)
            }
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }
}
