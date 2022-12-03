package ru.ifmo.se.lab4.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.ifmo.se.lab4.data.repository.jpa.UserJpaRepository
import ru.ifmo.se.lab4.presentation.scheme.ResponseStatus
import java.nio.file.attribute.UserPrincipalNotFoundException
import java.time.Instant
import java.util.stream.Collectors

@Service
class TokenGenerator(
    @Value("\${token.bearer.expiration}") private val bearerTokenExpiration: Long,
    private val encoder: JwtEncoder,
    private val userJpaRepository: UserJpaRepository
)
{
    fun generateBearerToken(userPrincipal: UserPrincipal): String {
        val userEntity = userJpaRepository.findByUsername(userPrincipal.username) ?:
            throw ResponseStatusException(HttpStatus.NOT_FOUND, userPrincipal.username)
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(bearerTokenExpiration))
            .subject(userEntity.id.toString())
            .build()
        return encoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}
