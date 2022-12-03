package ru.ifmo.se.lab4.presentation.controller

import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.ifmo.se.lab4.domain.model.PointBuilder
import ru.ifmo.se.lab4.domain.service.PointService
import ru.ifmo.se.lab4.domain.service.UserService
import ru.ifmo.se.lab4.presentation.mapper.toPoint
import ru.ifmo.se.lab4.presentation.mapper.toPointResponseScheme
import ru.ifmo.se.lab4.presentation.scheme.point.PointRequestScheme
import ru.ifmo.se.lab4.presentation.scheme.point.PointResponseScheme
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.validation.Valid

@RestController
@RequestMapping("/api/point")
class PointController(
    private val userService: UserService,
    private val pointService: PointService
) {
    @PostMapping
    fun hit(authentication: Authentication, @Validated @RequestBody pointRequest: PointRequestScheme): PointResponseScheme {
        // todo: validate request
        val startDelay = System.nanoTime()
        val user = userService.getUserByUsername(authentication.name)
        val pointBuilder = PointBuilder(
            user,
            pointService.checkHit(pointRequest.toPoint()),
            startDelay,
            LocalDateTime.now(ZoneOffset.UTC),
            pointRequest.x,
            pointRequest.y,
            pointRequest.r
        )
        return pointService.createPoint(pointBuilder)!!.toPointResponseScheme()
    }

    @GetMapping
    fun getAll(authentication: Authentication): List<PointResponseScheme> {
        val user = userService.getUserByUsername(authentication.name)
        return user.let { pointService.getAllPoints(it).map {point -> point.toPointResponseScheme() } }
    }

    @DeleteMapping
    fun clear(authentication: Authentication) {
        val user = userService.getUserByUsername(authentication.name)
        user.let { pointService.deleteAllPoints(user) }
    }
}
