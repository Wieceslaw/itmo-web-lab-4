package ru.ifmo.se.lab4.presentation.scheme.auth

data class TokensResponseScheme(
    val access: String,
    val refresh: String,
)
