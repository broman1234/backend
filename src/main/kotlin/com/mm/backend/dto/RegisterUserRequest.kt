package com.mm.backend.dto

data class RegisterUserRequest(
    val username: String,
    val password: String,
    val roles: List<String>
)

data class LoginUserRequest(
    val username: String,
    val password: String
)

