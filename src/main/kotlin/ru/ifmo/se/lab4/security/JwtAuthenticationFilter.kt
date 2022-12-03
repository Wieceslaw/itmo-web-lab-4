package ru.ifmo.se.lab4.security

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter (
    private val authenticationManager: AuthenticationManager,
): OncePerRequestFilter()
{
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val bearer = request.getHeader("Authorization")
            if (
                bearer != null &&
                bearer.startsWith("Bearer ") &&
                authenticationIsRequired()
                )
            {
                val token = resolveToken(bearer) ?: "Empty token"
                var authentication = JwtTokenAuthentication(token)
                authentication = authenticationManager.authenticate(authentication) as JwtTokenAuthentication
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (ex: AuthenticationException) {
            SecurityContextHolder.clearContext()
            throw ex
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(bearer: String): String? {
        val token = bearer.replace("Bearer ", "")
        if (token.isEmpty()) {
            return null
        }
        return token
    }

    private fun authenticationIsRequired(): Boolean {
        val existingAuth = SecurityContextHolder.getContext().authentication
        return existingAuth == null || existingAuth  is AnonymousAuthenticationToken
    }
}
