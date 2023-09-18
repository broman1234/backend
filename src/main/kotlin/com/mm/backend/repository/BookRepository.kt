package com.mm.backend.repository

import com.mm.backend.dto.BookRequest
import com.mm.backend.models.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {

    fun findAllByRequest(bookRequest: BookRequest, pageable: Pageable): Page<Book> {
        TODO("Not yet implemented")
    }
}