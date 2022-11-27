package ru.ifmo.se.lab4.domain.model

import java.time.LocalDateTime

data class PointBuilder(
    var user: User,
    var result: Boolean,
    var startDelay: Long,
    var datetime: LocalDateTime,
    var x: Double,
    var y: Double,
    var r: Double,
)