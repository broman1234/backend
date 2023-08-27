package com.mm.backend.service

import com.mm.backend.models.Role
import com.mm.backend.repository.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {

    fun getRoles(): List<Role> = roleRepository.findAll()
}