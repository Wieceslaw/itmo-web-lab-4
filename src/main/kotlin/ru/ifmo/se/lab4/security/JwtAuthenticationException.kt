package ru.ifmo.se.lab4.security

import org.springframework.security.core.AuthenticationException

class JwtAuthenticationException: AuthenticationException {
    constructor(msg: String?, cause: Throwable?) : super(msg, cause)
    constructor(msg: String?) : super(msg)
}
