package ru.ifmo.se.lab4.domain.repository

import ru.ifmo.se.lab4.domain.model.User

interface UserRepository {
    fun saveUser(user: User): User
    fun findUserById(id: Long): User?
    fun findUserByUsername(username: String): User?
    fun updateUserByUsername(user: User, username: String): User?
    fun deleteUserById(id: Long)
    fun deleteUser(user: User)
}
