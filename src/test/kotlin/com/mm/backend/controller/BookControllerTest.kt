package com.mm.backend.controller

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
        every { bookService.addBook(any()) }.returns(Unit)

        bookController.addBook(book1)

        verify(exactly = 1) { bookService.addBook(book1) }
    }

    @Test
    @DisplayName("should get book list successfully")
    fun getBooks() {
        every { bookService.getBooks()}.returns(listOf(book1, book2))

        val books = bookController.getBooks()

        verify(exactly = 1) { bookService.getBooks()}
        assertThat(books).isEqualTo(listOf(book1, book2))
    }
}