package ru.ifmo.se.lab4.domain.repository

import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.model.UserBuilder

interface UserRepository {
    fun createUser(user: UserBuilder): User
    fun existsByUsername(username: String): Boolean
    fun findUserById(id: Long): User?
    fun findUserByUsername(username: String): User?
    fun updateUser(user: User): User?
    fun deleteUserById(id: Long)
    fun deleteUser(user: User)
}
