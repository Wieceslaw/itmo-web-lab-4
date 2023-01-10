package ru.ifmo.se.lab4.presentation.scheme.auth

data class LoginRequestScheme(
    val username: String,
    val password: String,
)
