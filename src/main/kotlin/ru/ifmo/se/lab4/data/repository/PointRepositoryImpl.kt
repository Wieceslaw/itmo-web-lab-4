package ru.ifmo.se.lab4.data.repository

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.ifmo.se.lab4.data.mapper.toPointEntity
import ru.ifmo.se.lab4.data.mapper.toPointResult
import ru.ifmo.se.lab4.data.repository.jpa.PointJpaRepository
import ru.ifmo.se.lab4.data.repository.jpa.PointPagingRepository
import ru.ifmo.se.lab4.data.repository.jpa.UserJpaRepository
import ru.ifmo.se.lab4.domain.model.PointBuilder
import ru.ifmo.se.lab4.domain.model.PointResult
import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.repository.PointRepository
import ru.ifmo.se.lab4.util.exceptions.UserNotFoundException

@Component
class PointRepositoryImpl(
    private val pointJpaRepository: PointJpaRepository,
    private val userJpaRepository: UserJpaRepository,
    private val pointPagingRepository: PointPagingRepository,
): PointRepository
{
    @Transactional
    override fun savePoint(pointBuilder: PointBuilder): PointResult? =
        pointBuilder.toPointEntity(userJpaRepository)?.let {
            pointJpaRepository.save(it)
            it.toPointResult()
        }

    override fun findPointById(id: Long): PointResult? =
        pointJpaRepository.findById(id).let { if (it.isPresent) it.get().toPointResult() else null }

    override fun findAllPointsByUser(user: User): List<PointResult> {
        val userEntity = userJpaRepository.findByUsername(user.username) ?:
            throw UserNotFoundException(user.username)
        return userEntity.points.map { it.toPointResult()!! }
    }

    override fun countPointsByUser(user: User): Long {
        val userEntity = userJpaRepository.findByUsername(user.username) ?:
            throw UserNotFoundException(user.username)
        return pointJpaRepository.countByUser(userEntity)
    }

    override fun findAllPaginatedPointsByUser(user: User, pageable: Pageable): List<PointResult> {
        return pointPagingRepository.findPointEntityByUserUsername(user.username, pageable)
            .map { it.toPointResult()!! }
    }

    @Transactional
    override fun deletePointById(id: Long) {
        pointJpaRepository.deleteById(id)
    }

    @Transactional
    override fun deleteAllPointsByUser(user: User) {
        val userEntity = userJpaRepository.findByUsername(user.username) ?:
            throw UserNotFoundException(user.username)
        pointJpaRepository.deleteAllByUser(userEntity)
    }
}
