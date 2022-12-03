package ru.ifmo.se.lab4.domain.model

import java.time.LocalDateTime

class PointResult(
    val user: User,
    val result: Boolean,
    val delay: Long,
    val datetime: LocalDateTime,
    x: Double,
    y: Double,
    r: Double,
): Point(x, y, r)
