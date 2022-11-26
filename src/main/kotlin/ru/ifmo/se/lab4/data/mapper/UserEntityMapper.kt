package ru.ifmo.se.lab4.data.mapper

import ru.ifmo.se.lab4.data.entity.UserEntity
import ru.ifmo.se.lab4.domain.model.User

fun User.toUserEntity() = UserEntity(this.username, this.password)
fun UserEntity.toUser() = User(this.username, this.password)
