package com.mm.backend.configuration

import com.mm.backend.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
        val authorities = ArrayList<SimpleGrantedAuthority>()
        user.roles.forEach { role ->
            authorities.add(SimpleGrantedAuthority(role.roleName))
        }
        return org.springframework.security.core.userdetails.User(user.username, user.password, authorities)
    }
}