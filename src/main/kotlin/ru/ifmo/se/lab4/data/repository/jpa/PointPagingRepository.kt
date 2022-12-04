package ru.ifmo.se.lab4.data.repository.jpa

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import ru.ifmo.se.lab4.data.entity.PointEntity

@Repository
interface PointPagingRepository: PagingAndSortingRepository<PointEntity, Long> {
    fun findPointEntityByUserUsername(username: String, pageable: Pageable): List<PointEntity>
}
