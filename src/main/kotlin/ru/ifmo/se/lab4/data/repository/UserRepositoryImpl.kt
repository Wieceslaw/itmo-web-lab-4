package ru.ifmo.se.lab4.data.repository

import org.springframework.stereotype.Component
import ru.ifmo.se.lab4.data.entity.TokenEntity
import ru.ifmo.se.lab4.data.mapper.toUser
import ru.ifmo.se.lab4.data.mapper.toUserEntity
import ru.ifmo.se.lab4.data.repository.jpa.UserJpaRepository
import ru.ifmo.se.lab4.domain.model.Token
import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.repository.UserRepository


@Component
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
): UserRepository
{
    override fun findUserByUsername(username: String): User? =
        userJpaRepository.findUserEntityByUsername(username)?.toUser()

    override fun save(user: User) { userJpaRepository.save(user.toUserEntity()) }
}
