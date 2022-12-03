package ru.ifmo.se.lab4.presentation.scheme.point

import javax.validation.constraints.Max
import javax.validation.constraints.NotNull

data class PointRequestScheme(
    @field:Max(1)
    @field:NotNull
    val x: Double? = null,
    @field:NotNull
    val y: Double? = null,
    @field:NotNull
    val r: Double? = null,
)
