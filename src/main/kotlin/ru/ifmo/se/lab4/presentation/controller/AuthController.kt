package ru.ifmo.se.lab4.presentation.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.ifmo.se.lab4.domain.service.UserService
import ru.ifmo.se.lab4.presentation.mapper.toBearerTokenResponseScheme
import ru.ifmo.se.lab4.presentation.mapper.toResponseScheme
import ru.ifmo.se.lab4.presentation.mapper.toUserBuilder
import ru.ifmo.se.lab4.presentation.scheme.ResponseScheme
import ru.ifmo.se.lab4.presentation.scheme.ResponseStatus
import ru.ifmo.se.lab4.presentation.scheme.auth.BearerTokenResponseScheme
import ru.ifmo.se.lab4.presentation.scheme.auth.RegistrationRequestScheme
import ru.ifmo.se.lab4.presentation.scheme.auth.UserResponseScheme
import ru.ifmo.se.lab4.security.TokenGenerator
import ru.ifmo.se.lab4.security.UserPrincipal


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val tokenGenerator: TokenGenerator,
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody registration: RegistrationRequestScheme):
            ResponseEntity<ResponseScheme<UserResponseScheme>>
    {
        val user = userService.createUser(registration.toUserBuilder())
        return ResponseEntity(
            ResponseScheme(
                "Successful registration",
                ResponseStatus.SUCCESS,
                user.toResponseScheme()
            ),
            HttpStatus.OK
        )
    }

    @PostMapping("/login")
    fun login(authentication: Authentication):
            ResponseEntity<ResponseScheme<BearerTokenResponseScheme>>
    {
        return ResponseEntity(
            ResponseScheme(
                "Successful login",
                ResponseStatus.SUCCESS,
                tokenGenerator
                    .generateBearerToken(authentication.name)
                    .toBearerTokenResponseScheme()
            ),
            HttpStatus.OK
        )
    }
}
