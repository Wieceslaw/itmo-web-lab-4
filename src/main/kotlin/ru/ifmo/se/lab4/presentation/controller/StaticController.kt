package ru.ifmo.se.lab4.presentation.controller

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class StaticController: ErrorController {
    @GetMapping("/", "/login", "/register", "/error")
    fun route(): String {
        return "index.html"
    }
}
