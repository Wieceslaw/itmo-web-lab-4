package ru.ifmo.se.lab4.data.repository

import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.ifmo.se.lab4.data.mapper.toPoint
import ru.ifmo.se.lab4.data.mapper.toPointEntity
import ru.ifmo.se.lab4.data.repository.jpa.PointJpaRepository
import ru.ifmo.se.lab4.data.repository.jpa.UserJpaRepository
import ru.ifmo.se.lab4.domain.model.Point
import ru.ifmo.se.lab4.domain.model.PointBuilder
import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.repository.PointRepository

@Component
class PointRepositoryImpl(
    private val pointJpaRepository: PointJpaRepository,
    private val userJpaRepository: UserJpaRepository
): PointRepository
{
    @Transactional
    override fun savePoint(pointBuilder: PointBuilder): Point? =
        pointBuilder.toPointEntity(userJpaRepository)?.let {
            pointJpaRepository.save(it)
            it.toPoint()
        }

    override fun findPointById(id: Long): Point? =
        pointJpaRepository.findById(id).let { if (it.isPresent) it.get().toPoint() else null }

    override fun findAllPointsByUser(user: User): List<Point> {
        val userEntity = userJpaRepository.findByUsername(user.username) ?: throw UsernameNotFoundException(user.username)
        return userEntity.points.map { it.toPoint()!! }
    }

    @Transactional
    override fun deletePointById(id: Long) {
        pointJpaRepository.deleteById(id)
    }

    @Transactional
    override fun deleteAllPointsByUser(user: User) {
        val userEntity = userJpaRepository.findByUsername(user.username)
        userEntity?.also {
            pointJpaRepository.deleteAllByUser(it)
        }
    }
}
