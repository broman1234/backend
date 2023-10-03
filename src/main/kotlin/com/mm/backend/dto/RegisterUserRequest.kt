package com.mm.backend.dto

import com.mm.backend.enums.Role

data class RegisterUserRequest(
    val username: String,
    val password: String,
    val role: Role
)

data class LoginUserRequest(
    val username: String,
    val password: String
)

