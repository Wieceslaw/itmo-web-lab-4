package ru.ifmo.se.lab4.presentation.scheme.point

import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PointRequestScheme(
    @field:Max(1)
    val x: Double,
    @field:NotNull
    val y: Double,
    @field:NotNull
    val r: Double,
)
