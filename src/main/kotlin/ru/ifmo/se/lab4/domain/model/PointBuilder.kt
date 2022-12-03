package ru.ifmo.se.lab4.domain.model

import java.time.LocalDateTime

class PointBuilder(
    var user: User,
    var result: Boolean,
    var startDelay: Long,
    var datetime: LocalDateTime,
    x: Double,
    y: Double,
    r: Double,
): Point(x, y, r)