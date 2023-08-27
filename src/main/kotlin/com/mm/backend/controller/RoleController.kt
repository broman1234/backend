package com.mm.backend.controller

import com.mm.backend.models.Role
import com.mm.backend.service.RoleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/roles")
class RoleController (
    private val roleService: RoleService
        ){

    @GetMapping("")
    fun getRoles(): List<Role> {
        return roleService.getRoles()
    }
}