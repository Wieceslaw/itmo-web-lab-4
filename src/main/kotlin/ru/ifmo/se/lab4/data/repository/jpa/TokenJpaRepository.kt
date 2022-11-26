package ru.ifmo.se.lab4.data.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.ifmo.se.lab4.data.entity.TokenEntity

@Repository
interface TokenJpaRepository: JpaRepository<TokenEntity, Long> {
    fun existsByToken(token: String): Boolean
}
