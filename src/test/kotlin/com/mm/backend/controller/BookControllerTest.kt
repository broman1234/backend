package com.mm.backend.controller

import com.mm.backend.dto.BookRequest
import com.mm.backend.models.Book
import com.mm.backend.service.BookService
import com.mm.backend.testmodels.BookTestModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

@ExtendWith(MockKExtension::class)
@DisplayName("BookControllerTest")
internal class BookControllerTest {

    @MockK
    private lateinit var bookService: BookService

    @InjectMockKs
    private lateinit var bookController: BookController

    private val book1 = BookTestModel.book1
    private val book2 = BookTestModel.book2

    @Test
    @DisplayName("should return book successfully when adding a book")
    fun addBook() {
        every { bookService.addBook(any()) }.returns(book1)

        val book = bookController.addBook(book1)

        verify(exactly = 1) { bookService.addBook(book1) }
        assertThat(book).isEqualTo(book1)
    }

    @Test
    @DisplayName("should get book list successfully")
    fun getBooks() {
        val pageable: Pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("title")))
        val books = listOf(book1, book2)
        val pagedBooks: Page<Book> = PageImpl(books, pageable, books.size.toLong())
        val bookRequest = BookRequest()
        every { bookService.getBooksByRequest(any(), any()) }.returns(pagedBooks)

        val actualPagedBooks = bookController.getBooksByRequest(bookRequest, pageable)

        verify(exactly = 1) { bookService.getBooksByRequest(bookRequest, pageable) }
        assertThat(actualPagedBooks).isEqualTo(pagedBooks)
    }

    @Test
    @DisplayName("should get book given book title")
    fun getBookByTitle() {
        val pageable: Pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("title")))
        val books = listOf(book1, book2)
        val pagedBooks: Page<Book> = PageImpl(books, pageable, books.size.toLong())
        val bookRequest = BookRequest("the picture of Dorian Gray", null, null, null)
        every { bookService.getBooksByRequest(any(), any()) }.returns(pagedBooks)

        val actualBooks =
            bookController.getBooksByRequest(
                BookRequest(
                    "the picture of Dorian Gray",
                    null,
                    null,
                    null), pageable)

        verify(exactly = 1) { bookService.getBooksByRequest(bookRequest, pageable) }
        assertThat(actualBooks).isEqualTo(pagedBooks)
    }
}