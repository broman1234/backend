package com.mm.backend.models

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

    @ManyToMany
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: List<Role> =  mutableListOf()
)

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val roleName: String,

    @ManyToMany(mappedBy = "roles")
    val users: List<User> = mutableListOf()
)
