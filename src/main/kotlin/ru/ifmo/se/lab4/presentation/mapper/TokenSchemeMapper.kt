package ru.ifmo.se.lab4.presentation.mapper

import ru.ifmo.se.lab4.presentation.scheme.auth.BearerTokenResponseScheme
import ru.ifmo.se.lab4.security.BearerToken

fun BearerToken.toBearerTokenResponseScheme() = BearerTokenResponseScheme(
    this.token,
    this.expirationTime,
    this.expiresAt
)
