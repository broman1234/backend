package com.mm.backend.service

import com.mm.backend.models.Role
import com.mm.backend.models.User
import com.mm.backend.repository.RoleRepository
import com.mm.backend.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun registerUser(username: String, password: String, roles: List<Role>): User {

        val encoder = BCryptPasswordEncoder()
        val result: String = encoder.encode(password)
        val user = User(username = username, password = result, roles = roles)
        return userRepository.save(user)
    }
}