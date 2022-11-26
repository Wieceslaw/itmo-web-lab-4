package ru.ifmo.se.lab4.domain.repository

import ru.ifmo.se.lab4.domain.model.User

interface UserRepository {
    fun findUserByUsername(username: String): User?
    fun save(user: User)
}