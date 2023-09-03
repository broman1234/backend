package com.mm.backend.service

import com.mm.backend.models.Book
import com.mm.backend.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {

    fun addBook(book: Book): Book {
        return bookRepository.save(book)
    }
}