package ru.ifmo.se.lab4.data.entity

import javax.persistence.*

@Entity
@Table(name = "token_blacklist")
class TokenEntity(
    @Column(name = "token", nullable = false, length = 4096)
    val token: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
}
