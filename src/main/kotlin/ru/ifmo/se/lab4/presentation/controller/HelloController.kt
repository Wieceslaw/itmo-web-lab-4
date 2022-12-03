package ru.ifmo.se.lab4.presentation.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HelloController {
    @GetMapping("/hello")
    fun hello(authentication: Authentication): String {
        return "Hello ${authentication.name}!"
    }
}
