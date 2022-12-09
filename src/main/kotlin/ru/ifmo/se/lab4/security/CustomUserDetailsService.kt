package ru.ifmo.se.lab4.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.repository.UserRepository
import ru.ifmo.se.lab4.util.exceptions.UserNotFoundException

@Component
class CustomUserDetailsService(
    private val userRepository: UserRepository
): UserDetailsService
{
    override fun loadUserByUsername(username: String): UserDetails {

        val user: User = userRepository.findByUsername(username) ?:
            throw UserNotFoundException(username)
        return UserPrincipal(user)
    }

    fun loadUserById(id: Long): UserDetails {
        val user: User = userRepository.findById(id) ?:
            throw UserNotFoundException(id)
        return UserPrincipal(user)
    }
}