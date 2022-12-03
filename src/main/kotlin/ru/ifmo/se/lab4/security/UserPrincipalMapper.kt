package ru.ifmo.se.lab4.security

import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.security.UserPrincipal

fun User.toUserPrincipal() = UserPrincipal(this)
fun UserPrincipal.toUser() = this.user
