package com.mm.backend.service

import com.mm.backend.models.Book
import org.springframework.stereotype.Service

@Service
class BookService {

    fun addBook(book: Book) : Book{
        return book
    }
}