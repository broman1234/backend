package com.mm.backend.controller

import com.mm.backend.service.UserService
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthController(
    private val userService: UserService
) {

    companion object : KLogging()


    @RequestMapping("/login")
    fun login(@RequestParam("username") username: String, @RequestParam("password") password: String) {
        logger.info("authenticate user and generate token for user $username")
    }

    @RequestMapping("/register")
    fun register(@RequestParam("username") username: String, @RequestParam("password") password: String): ResponseEntity<Any> {
        val registeredUser = userService.registerUser(username, password)
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser)
    }
}