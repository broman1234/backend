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
    val coverImage: String,
    val rating: Int? = null,
    val ratingCount: Long? = null,
    val wantToReadCount: Long? = null,
    val haveReadCount: Long? = null,
    @Formula("COALESCE(want_to_read_count, 0) + COALESCE(have_read_count, 0)")
    val totalReaders: Long? = null,
    @Formula("COALESCE(rating, 0) * COALESCE(rating_count, 0)")
    val ratingRank: Int? = null
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