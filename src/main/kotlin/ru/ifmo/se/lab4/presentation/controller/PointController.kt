package ru.ifmo.se.lab4.presentation.controller

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
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
    fun findPointsWithPagination(
        authentication: JwtTokenAuthentication,
        @PageableDefault(page = 0, size = 5, sort = ["datetime"], direction = Sort.Direction.DESC)
        pageable: Pageable
    ): ResponseEntity<ResponseScheme<List<PointResponseScheme>>>
    {
        return ResponseEntity(
            ResponseScheme(
                "Successfully found points",
                ResponseStatus.SUCCESS,
                pointService.findPointsWithPagination(
                    authentication.user,
                    pageable,
                ).map { it.toPointResponseScheme() }
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
