package ru.ifmo.se.lab4.domain.repository

import org.springframework.data.domain.Pageable
import ru.ifmo.se.lab4.domain.model.PointResult
import ru.ifmo.se.lab4.domain.model.PointBuilder
import ru.ifmo.se.lab4.domain.model.User

interface PointRepository {
    fun savePoint(pointBuilder: PointBuilder): PointResult?
    fun findPointById(id: Long): PointResult?
    fun findAllPointsByUser(user: User): List<PointResult>
    fun countPointsByUser(user: User): Long
    fun deletePointById(id: Long)
    fun deleteAllPointsByUser(user: User)
    fun findAllPaginatedPointsByUser(user: User, pageable: Pageable): List<PointResult>
}