package ru.ifmo.se.lab4.domain.service

import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.repository.UserRepository
import ru.ifmo.se.lab4.security.UserPrincipal

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
): UserDetailsService
{
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findUserByUsername(username) ?: throw UsernameNotFoundException(username)
        return UserPrincipal(user)
    }

    fun createUser(user: User) {
        if (userRepository.findUserByUsername(user.username) != null)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already in use")
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)
    }
}