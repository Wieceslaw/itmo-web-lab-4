package ru.ifmo.se.lab4.domain.repository

import ru.ifmo.se.lab4.domain.model.Point
import ru.ifmo.se.lab4.domain.model.PointBuilder
import ru.ifmo.se.lab4.domain.model.User

interface PointRepository {
    fun savePoint(pointBuilder: PointBuilder): Point?
    fun findPointById(id: Long): Point?
    fun findAllPointsByUser(user: User): List<Point>
    fun deletePointById(id: Long)
    fun deleteAllPointsByUser(user: User)
}