package com.mm.backend.controller

import com.mm.backend.models.Book
import com.mm.backend.service.BookService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/books")
class BookController(
    private val bookService: BookService
) {

    @PostMapping
    fun addBook(@RequestBody book: Book): Book {
        return bookService.addBook(book)
    }
}