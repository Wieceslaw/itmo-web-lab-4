package ru.ifmo.se.lab4.data.repository

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.ifmo.se.lab4.data.mapper.toUser
import ru.ifmo.se.lab4.data.mapper.toUserEntity
import ru.ifmo.se.lab4.data.repository.jpa.UserJpaRepository
import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.model.UserBuilder
import ru.ifmo.se.lab4.domain.repository.UserRepository


@Component
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository
): UserRepository
{
    override fun create(userBuilder: UserBuilder): User {
        return userJpaRepository.save(userBuilder.toUserEntity()).toUser()
    }

    override fun existsByUsername(username: String): Boolean {
        return userJpaRepository.existsByUsername(username)
    }

    override fun findById(id: Long): User? =
        userJpaRepository.findById(id).let { if (it.isPresent) it.get().toUser() else null }

    override fun findByUsername(username: String): User? =
        userJpaRepository.findByUsername(username)?.toUser()

    override fun delete(user: User) {
        userJpaRepository.deleteByUsername(user.username)
    }

    override fun deleteById(id: Long) {
        userJpaRepository.deleteById(id)
    }

    @Transactional
    override fun update(user: User): User? {
        val userEntity = userJpaRepository.findByUsername(user.username)
        return userEntity?.let {
            it.password = user.password
            userJpaRepository.save(it)
            it.toUser()
        }
    }
}
