package com.mm.backend.service

import com.mm.backend.models.User
import com.mm.backend.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun registerUser(username: String, password: String): User {

        val encoder = BCryptPasswordEncoder()
        val result: String = encoder.encode(password)
        return userRepository.save(User(username = username, password = result))
    }
}