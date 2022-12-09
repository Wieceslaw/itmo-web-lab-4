package ru.ifmo.se.lab4.domain.service

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.model.UserBuilder
import ru.ifmo.se.lab4.domain.repository.UserRepository
import ru.ifmo.se.lab4.util.exceptions.UserNotFoundException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
)
{
    fun createUser(userBuilder: UserBuilder): User {
        if (userRepository.existsByUsername(userBuilder.username))
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use")
        userBuilder.password = passwordEncoder.encode(userBuilder.password)
        return userRepository.createUser(userBuilder)
    }
}
