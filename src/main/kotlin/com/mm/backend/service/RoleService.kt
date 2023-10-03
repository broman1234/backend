package com.mm.backend.service

import com.mm.backend.enums.Role
import org.springframework.stereotype.Service

@Service
class RoleService(

) {

    fun getRoles(): List<String> = Role.values().map { it.name }
}