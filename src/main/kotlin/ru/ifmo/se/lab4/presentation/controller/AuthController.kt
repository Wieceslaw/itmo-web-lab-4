package ru.ifmo.se.lab4.presentation.controller

import lombok.AllArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import ru.ifmo.se.lab4.domain.model.Token
import ru.ifmo.se.lab4.domain.service.TokenService
import ru.ifmo.se.lab4.domain.service.UserService
import ru.ifmo.se.lab4.presentation.mapper.toResponseScheme
import ru.ifmo.se.lab4.presentation.mapper.toUser
import ru.ifmo.se.lab4.presentation.scheme.RefreshTokenRequestScheme
import ru.ifmo.se.lab4.presentation.scheme.RegistrationRequestScheme
import ru.ifmo.se.lab4.presentation.scheme.TokensResponseScheme
import ru.ifmo.se.lab4.presentation.scheme.UserResponseScheme


@RestController
@RequestMapping("/auth")
class AuthController(
    private val tokenService: TokenService,
    private val userService: UserService,
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
    private val userDetailsService: UserDetailsService
) {
    @PostMapping("/register")
    fun register(@RequestBody registration: RegistrationRequestScheme): UserResponseScheme {
        val user = registration.toUser()
        userService.createUser(user)
        return user.toResponseScheme()
    }

    @PostMapping("/login")
    fun login(authentication: Authentication): TokensResponseScheme {
        val username = authentication.name
        val refresh = tokenService.getRefreshToken(username)
        val access = tokenService.getAccessToken(username)
        return TokensResponseScheme(access, refresh)
    }

    // TODO: remove
    private fun refreshTokenIsValid(token: Jwt): Boolean {
        try {
            userDetailsService.loadUserByUsername(token.subject)
        } catch (ignored: UsernameNotFoundException) {
            return false
        }
        return !tokenService.isBlackListedToken(Token(token.tokenValue)) &&
                token.hasClaim("type") && token.getClaim<Any>("type") == "refresh"
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody requestToken: RefreshTokenRequestScheme): TokensResponseScheme {
        val token: String = requestToken.refresh
        val authentication =
            jwtAuthenticationProvider.authenticate(BearerTokenAuthenticationToken(token)) as JwtAuthenticationToken
        if (refreshTokenIsValid(authentication.token)) {
            tokenService.addToBlackListToken(Token(authentication.token.tokenValue))
            val access = tokenService.getAccessToken(authentication.name)
            val refresh = tokenService.getRefreshToken(authentication.name)
            return TokensResponseScheme(access, refresh)
        }
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong token")
    }

    @PostMapping("/logout")
    fun logout(@RequestBody requestToken: RefreshTokenRequestScheme) {
        val token: String = requestToken.refresh
        val authentication =
            jwtAuthenticationProvider.authenticate(BearerTokenAuthenticationToken(token))
                    as JwtAuthenticationToken
        if (refreshTokenIsValid(authentication.token)) {
            userDetailsService.loadUserByUsername(authentication.name)
            tokenService.addToBlackListToken(Token(authentication.token.tokenValue))
        }
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong token")
    }
}
