package com.mm.backend.controller

import com.mm.backend.dto.GetBookRequest
import com.mm.backend.dto.UpdatedBookRequest
import com.mm.backend.models.Book
import com.mm.backend.service.BookService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
    fun addBook(@Valid @RequestBody book: Book): Book {
        return bookService.addBook(book)
    }

    @GetMapping
    fun getBooksByRequest(
        getBookRequest: GetBookRequest, @PageableDefault(
            page = 0,
            size = 5,
            sort = ["title"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable
    ): Page<Book> = bookService.getBooksByRequest(getBookRequest, pageable)

    @PutMapping("/{bookId}")
    fun editBookInfo(@PathVariable bookId: Long, @RequestBody updatedBook: UpdatedBookRequest): Book {
        return bookService.editBookInfo(updatedBook)
    }

    @GetMapping("/{bookId}")
    fun getBookInfo(@PathVariable bookId: Long): Book = bookService.getBookInfo(bookId)

    @DeleteMapping("/{bookIds}")
    fun deleteByIds(@PathVariable bookIds: List<Long>) = bookService.deleteByIds(bookIds)

    @GetMapping("/categories")
    fun getCategories(): List<String> = bookService.getCategories()
}