package ru.ifmo.se.lab4.presentation.scheme.point

data class PointResponseScheme(
    val result: Boolean,
    val delay: Long,
    val datetime: Long,
    val x: Double,
    val y: Double,
    val r: Double
)
