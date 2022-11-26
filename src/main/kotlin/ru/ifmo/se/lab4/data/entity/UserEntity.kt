package ru.ifmo.se.lab4.data.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "username", nullable = false, unique = true)
    val username: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null
)
