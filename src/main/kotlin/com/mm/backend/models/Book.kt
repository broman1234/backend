package com.mm.backend.models

import com.mm.backend.common.BaseEntity
import com.mm.backend.enums.Category
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.Formula
import javax.persistence.*

@Entity
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @NotBlank(message = "Title is required")
    var title: String,
    @NotBlank(message = "Author is required")
    var author: String,
    @NotBlank(message = "Category is required")
    @Enumerated(EnumType.STRING)
    var category: Category,
    @NotBlank(message = "Publisher is required")
    var publisher: String,
    var description: String? = null,
    val coverImage: String? = null,
    val rating: Int? = null,
    val ratingCount: Long? = null,
    val wantToReadCount: Long? = null,
    val haveReadCount: Long? = null,
    @Formula("want_to_read_count + have_read_count")
    val totalReaders: Long? = null
) : BaseEntity()

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