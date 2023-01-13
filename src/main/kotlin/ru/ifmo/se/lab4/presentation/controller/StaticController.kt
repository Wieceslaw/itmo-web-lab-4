package ru.ifmo.se.lab4.presentation.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class StaticController {
    @GetMapping("/", "/login", "/register")
    fun route(): String {
        return "index.html"
    }
}
