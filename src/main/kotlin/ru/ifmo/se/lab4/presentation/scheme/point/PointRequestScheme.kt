package ru.ifmo.se.lab4.presentation.scheme.point

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class PointRequestScheme(
    @field:Max(5)
    @field:Min(-3)
    @field:NotNull
    val x: Double? = null,
    @field:Max(3)
    @field:Min(-5)
    @field:NotNull
    val y: Double? = null,
    @field:Max(5)
    @field:Min(-3)
    @field:NotNull
    val r: Double? = null,
)
