package com.mm.backend.service

import com.mm.backend.models.Role
import com.mm.backend.models.User
import com.mm.backend.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun registerUser(username: String, password: String, roles: List<String>): User {

        val encoder = BCryptPasswordEncoder()
        val result: String = encoder.encode(password)
        val userRoles = mutableListOf<Role>()
        roles.forEach {
            userRoles.add(Role(roleName = it))
        }
        val user = User(username = username, password = result, roles = userRoles)
        return userRepository.save(user)
    }
}