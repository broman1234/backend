package com.mm.backend.service

import com.mm.backend.dto.BookRequest
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
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

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

        val book = bookService.addBook(book1)

        verify(exactly = 1) { bookRepository.save(book1) }
        assertThat(book).isEqualTo(book1)
    }

    @Test
    @DisplayName("should get books successfully")
    fun getBooks() {
        val pageable: Pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("title")))
        val books = listOf(book1, book2)
        val pagedBooks: Page<Book> = PageImpl(books, pageable, books.size.toLong())
        every { bookRepository.findAll(pageable) }.returns(pagedBooks)

        val actualPagedBook = bookService.getBooks(pageable)

        verify(exactly = 1) { bookRepository.findAll(pageable) }
        assertThat(actualPagedBook).isEqualTo(pagedBooks)
    }

    @Test
    @DisplayName("should get books given book request with only book title")
    fun getBooksByBookTitle() {
        val pageable: Pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("title")))
        val books = listOf(book1, book2)
        val pagedBooks: Page<Book> = PageImpl(books, pageable, books.size.toLong())
        every { bookRepository.findAllByRequest(any(), any()) }.returns(pagedBooks)
        val bookRequest = BookRequest("the picture of Dorian Gray", null, null, null)

        val actualBooks =
            bookService.getBooksByRequest(bookRequest, pageable)

        verify(exactly = 1) { bookRepository.findAllByRequest(bookRequest, pageable) }
        assertThat(actualBooks).isEqualTo(pagedBooks)
    }
}