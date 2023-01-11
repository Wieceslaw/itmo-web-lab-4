package ru.ifmo.se.lab4.security

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.ifmo.se.lab4.domain.model.User

@Component
class UserSecurityService(
    private val passwordEncoder: PasswordEncoder
)
{
    fun check_password(user: User, password: String): Boolean {
        return passwordEncoder.matches(password, user.password)
    }
}
