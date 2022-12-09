package ru.ifmo.se.lab4.domain.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.ifmo.se.lab4.domain.model.Point
import ru.ifmo.se.lab4.domain.model.PointBuilder
import ru.ifmo.se.lab4.domain.model.PointResult
import ru.ifmo.se.lab4.domain.model.User
import ru.ifmo.se.lab4.domain.repository.PointRepository

@Service
class PointService(
    private val pointRepository: PointRepository
)
{
    fun checkHit(p: Point): Boolean {
        return if (p.x >= 0 && p.y >= 0) {
            p.y <= ((-0.5 * p.x) + (0.5 * p.r))
        } else if (p.x < 0 && p.y >= 0) {
            (p.x * p.x) + (p.y * p.y) <= (p.r * p.r)
        } else if (p.x < 0 && p.y < 0) {
            false
        } else {
            (p.x <= (p.r / 2)) && (p.y >= -p.r)
        }
    }

    fun createPoint(pointBuilder: PointBuilder): PointResult? {
        return pointRepository.savePoint(pointBuilder)
    }

    fun findPointsWithPagination(user: User, pageable: Pageable): List<PointResult> {
        return pointRepository.findAllPaginatedPointsByUser(
            user,
            pageable,
        )
    }

    fun deleteAllPoints(user: User) {
        pointRepository.deleteAllPointsByUser(user)
    }
}
