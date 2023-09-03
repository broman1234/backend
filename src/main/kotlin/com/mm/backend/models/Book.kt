package com.mm.backend.models

import jakarta.validation.constraints.NotBlank
import javax.persistence.*

@Entity
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @NotBlank(message = "Title is required")
    val title: String,
    @NotBlank(message = "Author is required")
    val author: String,
    @NotBlank(message = "Category is required")
    val category: String,
    @NotBlank(message = "Publisher is required")
    val publisher: String,
    val description: String? = null,
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    val comments: List<Comment>? = null,
    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
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