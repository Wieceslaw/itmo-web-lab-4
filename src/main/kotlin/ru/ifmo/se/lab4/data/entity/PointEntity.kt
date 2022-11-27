package ru.ifmo.se.lab4.data.entity

import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "points")
class PointEntity(
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,
    @Column(nullable = false)
    val result: Boolean,
    @Column(nullable = false)
    val startDelay: Long,
    @Column(nullable = false)
    val datetime: LocalDateTime,
    @Column(nullable = false)
    val x: Double,
    @Column(nullable = false)
    val y: Double,
    @Column(nullable = false)
    val r: Double
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null

    @Column(nullable = false)
    var endDelay: Long? = null

    val delay: Long?
        get() = endDelay?.minus(startDelay)

    @PrePersist
    private fun onCreate() {
        endDelay = System.nanoTime()
    }
}
