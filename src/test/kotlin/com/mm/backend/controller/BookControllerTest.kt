package com.mm.backend.controller

import com.mm.backend.dto.BookRequest
import com.mm.backend.dto.UpdatedBookRequest
import com.mm.backend.models.Book
import com.mm.backend.service.BookService
import com.mm.backend.testmodels.BookTestModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.util.NoSuchElementException

@ExtendWith(MockKExtension::class)
@DisplayName("BookControllerTest")
internal class BookControllerTest {

    @MockK
    private lateinit var bookService: BookService

    @InjectMockKs
    private lateinit var bookController: BookController

    private val book1 = BookTestModel.book1
    private val book2 = BookTestModel.book2
    private val updatedBook2 = BookTestModel.updatedBook2
    private val updatedBookRequest = UpdatedBookRequest(book2.id, null, null, null, "Mars")

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
                    null
                ), pageable
            )

        verify(exactly = 1) { bookService.getBooksByRequest(bookRequest, pageable) }
        assertThat(actualBooks).isEqualTo(pagedBooks)
    }

    @Test
    @DisplayName("should update book info given updated book info successfully")
    fun editBookInfoSuccessfully() {
        val updatedBookRequest = UpdatedBookRequest(book2.id, null, null, null, "Mars")
        every { bookService.editBookInfo(any()) }.returns(updatedBook2)

        val actualUpdatedBook2 = bookController.editBookInfo(book2.id, updatedBookRequest)

        verify(exactly = 1) { bookService.editBookInfo(updatedBookRequest) }
        assertThat(actualUpdatedBook2).isEqualTo(updatedBook2)
    }

    @Test
    @DisplayName("should failed to update book info when book is not found given book id")
    fun editBookWhenBookNotFound() {
        every { bookService.editBookInfo(any()) }.throws(NoSuchElementException("Book not found for book id ${updatedBookRequest.id}"))

        assertThatThrownBy {
            bookController.editBookInfo(book2.id, updatedBookRequest)
        }.isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("Book not found for book id ${updatedBookRequest.id}")
    }

    @Test
    @DisplayName("should get a book details when book is found given book id")
    fun getBookSuccessfullyGivenBookId() {
        every { bookService.getBookInfo(any()) }.returns(book2)

        val actualBook = bookController.getBookInfo(book2.id)

        verify(exactly = 1) { bookService.getBookInfo(book2.id)}
        assertThat(actualBook).isEqualTo(book2)
    }

    @Test
    @DisplayName("should failed to get a book details when book is not found given book id")
    fun getBookWhenBookNotFound() {
        every { bookService.getBookInfo(any()) }.throws(NoSuchElementException("Book is not found for book id ${book2.id} !"))

        assertThatThrownBy {
            bookController.getBookInfo(book2.id)
        }.isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("Book is not found for book id ${book2.id} !")
    }
}