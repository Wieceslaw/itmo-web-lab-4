package ru.ifmo.se.lab4.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationDsl : AbstractHttpConfigurer<JwtAuthenticationDsl, HttpSecurity>() {
    override fun configure(http: HttpSecurity) {
        val authenticationManager = http.getSharedObject(AuthenticationManager::class.java)
        http.addFilterAfter(JwtAuthenticationFilter(authenticationManager), ExceptionTranslationFilter::class.java)
    }
}
