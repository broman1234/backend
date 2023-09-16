package com.mm.backend.service

import com.mm.backend.models.Book
import com.mm.backend.repository.BookRepository
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
@DisplayName("BookServiceTest")
internal class BookServiceTest {

    @MockK
    private lateinit var bookRepository: BookRepository

    @InjectMockKs
    private lateinit var bookService: BookService

    private val book1 = BookTestModel.book1
    private val book2 = BookTestModel.book2

    @Test
    @DisplayName("should return added book when adding a new book")
    fun addBook() {
        every { bookRepository.save(any()) }.returns(book1)

        bookService.addBook(book1)

        verify(exactly = 1) { bookRepository.save(book1) }
    }

    @Test
    @DisplayName("should get books successfully")
    fun getBooks() {
        every { bookRepository.findAll() }.returns(listOf(book1, book2))

        val books = bookService.getBooks()

        verify(exactly = 1) { bookRepository.findAll()}
        assertThat(books).isEqualTo(listOf(book1, book2))
    }
}