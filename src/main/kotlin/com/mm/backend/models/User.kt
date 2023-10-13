package com.mm.backend.models

import com.mm.backend.common.BaseEntity
import com.mm.backend.enums.Role
import lombok.RequiredArgsConstructor
import javax.persistence.*

@Entity
@Table(name = "users")
@RequiredArgsConstructor
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val username: String,

    val password: String,

    @Enumerated(EnumType.STRING)
    val role: Role,
) : BaseEntity()
