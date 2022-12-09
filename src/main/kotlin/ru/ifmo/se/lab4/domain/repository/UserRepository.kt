package ru.ifmo.se.lab4.domain.repository

import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.model.UserBuilder

interface UserRepository {
    fun create(user: UserBuilder): User
    fun existsByUsername(username: String): Boolean
    fun findById(id: Long): User?
    fun findByUsername(username: String): User?
    fun update(user: User): User?
    fun deleteById(id: Long)
    fun delete(user: User)
}
