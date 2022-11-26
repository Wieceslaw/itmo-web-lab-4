package ru.ifmo.se.lab4.domain.repository

import ru.ifmo.se.lab4.domain.model.Token

interface TokenRepository {
    fun exists(token: Token): Boolean
    fun save(token: Token)
}