package ru.ifmo.se.lab4.presentation.mapper

import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.presentation.scheme.RegistrationRequestScheme
import ru.ifmo.se.lab4.presentation.scheme.UserResponseScheme

fun RegistrationRequestScheme.toUser() = User(this.username, this.password)
fun User.toResponseScheme() = UserResponseScheme(this.username, this.password)
