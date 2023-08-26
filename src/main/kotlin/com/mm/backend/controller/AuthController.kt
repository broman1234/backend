package com.mm.backend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mm.backend.configuration.JwtTokenUtil
import com.mm.backend.dto.RegisterUserRequest
import com.mm.backend.repository.UserRepository
import com.mm.backend.service.UserService
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/auth")
class AuthController(
    private val userService: UserService,
    private val jwtTokenUtil: JwtTokenUtil,
    private val userRepository: UserRepository,
    private val userDetailsService: UserDetailsService
) {

    companion object : KLogging() {

    }


    @RequestMapping("/login")
    fun login(@RequestParam("username") username: String, @RequestParam("password") password: String) {
        logger.info("authenticate user and generate token for user $username")
    }

    @PostMapping("/register")
    fun register(@RequestBody registerUserRequest: RegisterUserRequest): ResponseEntity<Any> {
        val registeredUser = userService.registerUser(registerUserRequest.username, registerUserRequest.password, registerUserRequest.roles)
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser)
    }

    @GetMapping("/token/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
            val authorizationHeader = request.getHeader("Authorization")
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    val refreshToken = authorizationHeader.substring("Bearer ".length)
                    val username = jwtTokenUtil.getUsernameFromToken(refreshToken)
                    val user = userRepository.findByUsername(username)?: throw UsernameNotFoundException("User not found with username: $username")
                    val userDetails = userDetailsService.loadUserByUsername(user.username)
                    val newAccessToken = jwtTokenUtil.generateTokenWithRoles(userDetails)
                    val tokens = mapOf(
                        "access_token" to newAccessToken,
                        "refresh_token" to refreshToken
                    )
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, tokens)
                } catch (exception: Exception) {
                    response.setHeader("error", exception.message)
                    response.status = HttpStatus.FORBIDDEN.value()
                    val error = mapOf("error_message" to exception.message)
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, error)
                }
            } else {
                throw RuntimeException("Refresh token is missing in the header")
            }

    }
}