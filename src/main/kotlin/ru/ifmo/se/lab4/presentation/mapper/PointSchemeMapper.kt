package ru.ifmo.se.lab4.presentation.mapper

import ru.ifmo.se.lab4.domain.model.Hit
import ru.ifmo.se.lab4.domain.model.Point
import ru.ifmo.se.lab4.presentation.scheme.point.PointRequestScheme
import ru.ifmo.se.lab4.presentation.scheme.point.PointResponseScheme

fun PointRequestScheme.toHit() = Hit(
    this.x,
    this.y,
    this.r
)

fun Point.toPointResponseScheme() = PointResponseScheme(
    this.result,
    this.delay,
    this.datetime.toString(),
    this.x,
    this.y,
    this.r,
)
