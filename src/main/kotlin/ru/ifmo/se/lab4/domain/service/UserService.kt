package ru.ifmo.se.lab4.domain.service

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.repository.UserRepository
import ru.ifmo.se.lab4.util.exceptions.UserNotFoundException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
)
{
    fun createUser(user: User): User {
        if (userRepository.findUserByUsername(user.username) != null)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use")
        user.password = passwordEncoder.encode(user.password)
        return userRepository.saveUser(user)
    }

    fun getUserByUsername(username: String): User =
        userRepository.findUserByUsername(username) ?:
            throw UserNotFoundException("User not found")
}
