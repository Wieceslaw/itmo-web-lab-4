package ru.ifmo.se.lab4.domain.service

import org.springframework.stereotype.Service
import ru.ifmo.se.lab4.domain.model.Hit
import ru.ifmo.se.lab4.domain.model.Point
import ru.ifmo.se.lab4.domain.model.PointBuilder
import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.repository.PointRepository

@Service
class PointService(
    private val pointRepository: PointRepository
)
{
    fun checkHit(hit: Hit): Boolean {
        return false
        // TODO: implement
    }

    fun createPoint(pointBuilder: PointBuilder): Point? {
        return pointRepository.savePoint(pointBuilder)
    }

    fun getAllPoints(user: User): List<Point> = pointRepository.findAllPointsByUser(user)

    fun deleteAllPoints(user: User) { pointRepository.deleteAllPointsByUser(user) }
}
