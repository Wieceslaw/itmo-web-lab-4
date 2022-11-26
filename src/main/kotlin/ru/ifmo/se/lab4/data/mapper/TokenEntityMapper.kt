package ru.ifmo.se.lab4.data.mapper

import ru.ifmo.se.lab4.data.entity.TokenEntity
import ru.ifmo.se.lab4.domain.model.Token

fun TokenEntity.toToken() = Token(this.token)
fun Token.toTokenEntity() = TokenEntity(this.token)
