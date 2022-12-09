package ru.ifmo.se.lab4.security

data class BearerToken(
    val token: String,
    val expirationTime: Long,
    val expiresAt: Long,
)
