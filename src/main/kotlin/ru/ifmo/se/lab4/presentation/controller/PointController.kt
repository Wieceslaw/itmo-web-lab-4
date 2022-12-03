package ru.ifmo.se.lab4.presentation.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import ru.ifmo.se.lab4.domain.model.PointBuilder
import ru.ifmo.se.lab4.domain.service.PointService
import ru.ifmo.se.lab4.presentation.mapper.toPoint
import ru.ifmo.se.lab4.presentation.mapper.toPointResponseScheme
import ru.ifmo.se.lab4.presentation.scheme.ResponseScheme
import ru.ifmo.se.lab4.presentation.scheme.ResponseStatus
import ru.ifmo.se.lab4.presentation.scheme.point.PointRequestScheme
import ru.ifmo.se.lab4.presentation.scheme.point.PointResponseScheme
import ru.ifmo.se.lab4.security.JwtTokenAuthentication
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/api/point")
class PointController(
    private val pointService: PointService
) {
    @PostMapping
    fun hit(authentication: JwtTokenAuthentication, @Validated @RequestBody pointRequest: PointRequestScheme):
            ResponseEntity<ResponseScheme<PointResponseScheme>>
    {
        val startDelay = System.nanoTime()
        val user = authentication.user
        val pointBuilder = PointBuilder(
            user,
            pointService.checkHit(pointRequest.toPoint()),
            startDelay,
            LocalDateTime.now(ZoneOffset.UTC),
            pointRequest.x!!,
            pointRequest.y!!,
            pointRequest.r!!
        )

        return ResponseEntity(
            ResponseScheme(
                "Successfully created point",
                ResponseStatus.SUCCESS,
                pointService.createPoint(pointBuilder)!!.toPointResponseScheme()
            ),
            HttpStatus.OK
        )
    }

    @GetMapping
    fun getAll(authentication: JwtTokenAuthentication):
            ResponseEntity<ResponseScheme<List<PointResponseScheme>>>
    {
        val user = authentication.user

        return ResponseEntity(
            ResponseScheme(
                "Successfully got all points",
                ResponseStatus.SUCCESS,
                user.let {
                    pointService.getAllPoints(it).map {
                            point -> point.toPointResponseScheme()
                    }
                }
            ),
            HttpStatus.OK
        )
    }

    @DeleteMapping
    fun clear(authentication: JwtTokenAuthentication):
            ResponseEntity<ResponseScheme<Nothing>>
    {
        val user = authentication.user
        user.let { pointService.deleteAllPoints(user) }

        return ResponseEntity(
            ResponseScheme(
                "Successfully deleted all points",
                ResponseStatus.SUCCESS
            ),
            HttpStatus.OK
        )
    }
}
