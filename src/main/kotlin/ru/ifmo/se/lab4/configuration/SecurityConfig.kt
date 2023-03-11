package ru.ifmo.se.lab4.configuration

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain
import ru.ifmo.se.lab4.security.CustomAuthenticationEntryPoint
import ru.ifmo.se.lab4.security.CustomUserDetailsService
import ru.ifmo.se.lab4.security.JwtAuthenticationDsl
import ru.ifmo.se.lab4.security.JwtAuthenticationProvider
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
@EnableWebSecurity
class SecurityConfig(
    @Value("\${jwt.public.key}") private val rsaPublicKey: RSAPublicKey,
    @Value("\${jwt.private.key}") private val rsaPrivateKey: RSAPrivateKey,
    private val userDetailsService: CustomUserDetailsService
)
{
    @Bean
    fun apiFilterChain(
        http: HttpSecurity,
        jwtAuthDsl: JwtAuthenticationDsl,
        jwtAuthenticationProvider: JwtAuthenticationProvider,
        customAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    ): SecurityFilterChain {
        http
            .antMatcher("/api/**")
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .authorizeHttpRequests()
            .antMatchers("/api/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()

            .apply(jwtAuthDsl)
            .and()
            .authenticationProvider(jwtAuthenticationProvider)
            .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
        return http.build()
    }

    @Bean
    fun jwtAuthenticationEntryPoint(): CustomAuthenticationEntryPoint {
        return CustomAuthenticationEntryPoint()
    }

    @Bean
    fun jwtAuthenticationProvider(): JwtAuthenticationProvider {
        return JwtAuthenticationProvider(jwtDecoder(), userDetailsService)
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey.Builder(rsaPublicKey).privateKey(rsaPrivateKey).build()
        val jwks: JWKSource<SecurityContext> = ImmutableJWKSet(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }
}
