package com.mm.backend.controller

import com.mm.backend.models.Book
import com.mm.backend.service.BookService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/admin/books")
class BookController(
    private val bookService: BookService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@Valid @RequestBody book: Book): Book{
        return bookService.addBook(book)
    }

    @GetMapping
    fun getBooks(@PageableDefault(page = 0, size = 20, sort = ["title"], direction = Sort.Direction.ASC) pageable: Pageable): Page<Book> = bookService.getBooks(pageable)
}