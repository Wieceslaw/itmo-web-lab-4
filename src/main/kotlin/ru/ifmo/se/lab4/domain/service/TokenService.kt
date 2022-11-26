package ru.ifmo.se.lab4.domain.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import ru.ifmo.se.lab4.domain.model.Token
import ru.ifmo.se.lab4.domain.repository.TokenRepository
import ru.ifmo.se.lab4.domain.repository.UserRepository
import java.time.Instant

@Service
class TokenService(
    @Value("\${token.access.expiration}") private val accessTokenExpiration: Long,
    @Value("\${token.refresh.expiration}") private val refreshTokenExpiration: Long,
    private val encoder: JwtEncoder,
    private val tokenRepository: TokenRepository,
    private val userRepository: UserRepository
)
{
    fun getAccessToken(username: String): String {
        val now = Instant.now()
//        val user = userRepository.findUserByUsername(username)
//        val scope = user.authorities.stream()
//            .map { obj: GrantedAuthority -> obj.authority }
//            .collect(Collectors.joining(" "))
        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(accessTokenExpiration))
//            .claim("scope", scope)
            .subject(username)
            .claim("type", "access")
            .build()
        return encoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    fun getRefreshToken(username: String): String {
        val now = Instant.now()
        val claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(refreshTokenExpiration))
            .subject(username)
            .claim("type", "refresh")
            .build()
        return encoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

    fun addToBlackListToken(token: Token) {
        tokenRepository.save(token)
    }

    fun isBlackListedToken(token: Token): Boolean {
        return tokenRepository.exists(token)
    }
}