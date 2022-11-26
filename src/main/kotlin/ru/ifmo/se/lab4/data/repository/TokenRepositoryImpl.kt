package ru.ifmo.se.lab4.data.repository

import org.springframework.stereotype.Component
import ru.ifmo.se.lab4.data.mapper.toTokenEntity
import ru.ifmo.se.lab4.data.repository.jpa.TokenJpaRepository
import ru.ifmo.se.lab4.domain.model.Token
import ru.ifmo.se.lab4.domain.repository.TokenRepository

@Component
class TokenRepositoryImpl(
    private val tokenJpaRepository: TokenJpaRepository
): TokenRepository
{
    override fun exists(token: Token): Boolean = tokenJpaRepository.existsByToken(token.token)

    override fun save(token: Token) { tokenJpaRepository.save(token.toTokenEntity()) }
}
