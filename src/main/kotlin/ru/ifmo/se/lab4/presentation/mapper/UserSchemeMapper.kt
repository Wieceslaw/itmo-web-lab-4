package ru.ifmo.se.lab4.presentation.mapper

import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.model.UserBuilder
import ru.ifmo.se.lab4.presentation.scheme.auth.RegistrationRequestScheme
import ru.ifmo.se.lab4.presentation.scheme.auth.UserResponseScheme

fun RegistrationRequestScheme.toUserBuilder() = UserBuilder(this.username, this.password)
fun User.toResponseScheme() = UserResponseScheme(this.username)
