package ru.ifmo.se.lab4.presentation.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.ifmo.se.lab4.domain.service.UserService
import ru.ifmo.se.lab4.presentation.mapper.toBearerTokenResponseScheme
import ru.ifmo.se.lab4.presentation.mapper.toResponseScheme
import ru.ifmo.se.lab4.presentation.mapper.toUserBuilder
import ru.ifmo.se.lab4.presentation.scheme.ResponseScheme
import ru.ifmo.se.lab4.presentation.scheme.ResponseStatus
import ru.ifmo.se.lab4.presentation.scheme.auth.BearerTokenResponseScheme
import ru.ifmo.se.lab4.presentation.scheme.auth.LoginRequestScheme
import ru.ifmo.se.lab4.presentation.scheme.auth.RegistrationRequestScheme
import ru.ifmo.se.lab4.presentation.scheme.auth.UserResponseScheme
import ru.ifmo.se.lab4.security.TokenGenerator
import ru.ifmo.se.lab4.security.UserSecurityService


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val tokenGenerator: TokenGenerator,
    private val userService: UserService,
    private val userSecurityService: UserSecurityService
) {
    @PostMapping("/register")
    fun register(@RequestBody registrationData: RegistrationRequestScheme):
            ResponseEntity<ResponseScheme<UserResponseScheme>>
    {
        val user = userService.createUser(registrationData.toUserBuilder())
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
    fun login(@RequestBody loginData: LoginRequestScheme):
            ResponseEntity<ResponseScheme<BearerTokenResponseScheme>>
    {
        val user = userService.findUserByUsername(loginData.username) ?:
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad login credentials")
        if (!userSecurityService.checkPassword(user, loginData.password)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad login credentials")
        }
        return ResponseEntity(
            ResponseScheme(
                "Successful login",
                ResponseStatus.SUCCESS,
                tokenGenerator
                    .generateBearerToken(user.username)
                    .toBearerTokenResponseScheme()
            ),
            HttpStatus.OK
        )
    }
}
