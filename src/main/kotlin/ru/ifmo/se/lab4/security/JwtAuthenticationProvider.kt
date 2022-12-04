package ru.ifmo.se.lab4.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtException
import java.time.Instant

class JwtAuthenticationProvider(
    private val jwtDecoder: JwtDecoder,
    private val userDetailsService: CustomUserDetailsService
): AuthenticationProvider
{
    override fun authenticate(authentication: Authentication): Authentication {
        val bearer = authentication as JwtTokenAuthentication
        val token = getJwt(bearer)
        val user = resolveToken(token)
        authentication.userPrincipal = user
        authentication.isAuthenticated = true
        return authentication
    }

    private fun resolveToken(token: Jwt): UserDetails {
        // TODO: other validations
        val now = Instant.now()
        val expiresAt = token.expiresAt
        if (expiresAt == null || expiresAt <= now) {
            throw JwtAuthenticationException("Token is expired")
        }
        try {
            val id = token.subject.toLong()
            return userDetailsService.loadUserById(id)
        } catch (ex: NumberFormatException) {
            throw JwtAuthenticationException("Token subject is not an id")
        }
    }

    private fun getJwt(bearer: JwtTokenAuthentication): Jwt {
        return try {
            jwtDecoder.decode(bearer.credentials)
        } catch (ex: JwtException) {
            throw JwtAuthenticationException("Invalid token", ex)
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return JwtTokenAuthentication::class.java.isAssignableFrom(authentication)
    }
}
