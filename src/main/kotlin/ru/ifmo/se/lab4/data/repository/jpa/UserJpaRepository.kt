package ru.ifmo.se.lab4.data.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.ifmo.se.lab4.data.entity.UserEntity

@Repository
interface UserJpaRepository: JpaRepository<UserEntity, Long> {
    fun findUserEntityByUsername(username: String): UserEntity?
}
