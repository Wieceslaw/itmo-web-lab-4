package ru.ifmo.se.lab4.domain.model

import java.time.LocalDateTime

data class Point(
    val user: User,
    val result: Boolean,
    val delay: Long,
    val datetime: LocalDateTime,
    val x: Double,
    val y: Double,
    val r: Double,
)
