package com.mm.backend.models

import javax.persistence.*

@Entity
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val title: String,
    val author: String,
    val category: String,
    val description: String,
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    val comments: List<Comment>? = null,
    @ManyToMany
    val users: List<User>? = null
)

@Entity
@Table(name = "comments")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val content: String,
    @ManyToOne
    @JoinColumn(name = "book_id")
    val book: Book,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User
)