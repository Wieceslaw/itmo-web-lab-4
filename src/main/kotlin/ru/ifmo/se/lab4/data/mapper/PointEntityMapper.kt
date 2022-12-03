package ru.ifmo.se.lab4.data.mapper

import ru.ifmo.se.lab4.data.entity.PointEntity
import ru.ifmo.se.lab4.data.repository.jpa.UserJpaRepository
import ru.ifmo.se.lab4.domain.model.PointResult
import ru.ifmo.se.lab4.domain.model.PointBuilder

fun PointBuilder.toPointEntity(userJpaRepository: UserJpaRepository) =
    userJpaRepository.findByUsername(this.user.username)?.let {
        PointEntity(
            it,
            this.result,
            this.startDelay,
            this.datetime,
            this.x,
            this.y,
            this.r
        )
    }


fun PointEntity.toPointResult() = this.delay?.let {
    PointResult(
        this.user.toUser(),
        this.result,
        it,
        this.datetime,
        this.x,
        this.y,
        this.r
    )
}
