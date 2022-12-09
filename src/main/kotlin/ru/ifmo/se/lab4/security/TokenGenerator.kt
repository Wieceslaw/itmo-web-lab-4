package ru.ifmo.se.lab4.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.ifmo.se.lab4.domain.service.UserService
import java.time.Instant

@Service
class TokenGenerator(
    @Value("\${token.bearer.expiration}") private val tokenExpiration: Long,
    private val encoder: JwtEncoder,
    private val userService: UserService,
)
{
    fun generateBearerToken(username: String): BearerToken {
        val user = userService.findUserByUsername(username) ?:
            throw ResponseStatusException(HttpStatus.NOT_FOUND, username)
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(tokenExpiration))
            .subject(user.id.toString())
            .build()
        val token = encoder.encode(JwtEncoderParameters.from(claims)).tokenValue
        return BearerToken(
            token,
            tokenExpiration,
            now.plusSeconds(tokenExpiration).toEpochMilli() / 1000,
        )
    }
}
