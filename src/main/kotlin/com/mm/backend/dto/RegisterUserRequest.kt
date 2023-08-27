package com.mm.backend.dto

import com.mm.backend.models.Role

data class RegisterUserRequest(
    val username: String,
    val password: String,
    val roles: List<Role>
)

data class LoginUserRequest(
    val username: String,
    val password: String
)

