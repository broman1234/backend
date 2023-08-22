package com.mm.backend.controller

import mu.KLogging
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthController {

    companion object : KLogging()


    @RequestMapping("/login")
    fun login(@RequestParam("username") username: String, @RequestParam("password") password: String) {
        logger.info("authenticate user and generate token for user $username")
    }
}