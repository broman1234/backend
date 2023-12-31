package com.mm.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): String {
        return "hello"
    }
}