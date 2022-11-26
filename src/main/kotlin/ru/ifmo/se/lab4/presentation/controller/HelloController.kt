package ru.ifmo.se.lab4.presentation.controller

import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/auth")
class HelloController {
    @GetMapping("/api/hello")
    fun hello(authentication: Authentication): String = "Hello ${authentication.name}!"
}
