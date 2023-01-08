package ru.ifmo.se.lab4.presentation.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class HtmlController {
    @GetMapping("/", "/login")
    fun route(): String {
        return "index.html"
    }
}