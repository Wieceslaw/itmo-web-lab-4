package ru.ifmo.se.lab4.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.ifmo.se.lab4.domain.model.User

class JwtTokenAuthentication(private val token: String): Authentication {
    private var authenticated: Boolean = false
    var userPrincipal: UserDetails? = null
    val user: User
        get() = (userPrincipal as UserPrincipal).toUser()

    override fun getName(): String? {
        return userPrincipal?.username
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return userPrincipal?.authorities
    }

    override fun getCredentials(): String {
        return token
    }

    override fun getDetails(): String {
        return token
    }

    override fun getPrincipal(): UserDetails? {
        return userPrincipal
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        authenticated = isAuthenticated
    }
}