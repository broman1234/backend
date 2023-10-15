package com.mm.backend.service

import com.mm.backend.dto.GetBookRequest
import com.mm.backend.dto.UpdatedBookRequest
import com.mm.backend.dto.book.PopularRankBookResponseDTO
import com.mm.backend.dto.book.RatingRankBookResponseDTO
import com.mm.backend.models.Book
import com.mm.backend.repository.BookRepository
import com.mm.backend.testmodels.BookTestModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions
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
import java.util.*
import kotlin.NoSuchElementException

@ExtendWith(MockKExtension::class)
@DisplayName("BookServiceTest")
internal class BookServiceTest {

    @MockK
    private lateinit var bookRepository: BookRepository

    @InjectMockKs
    private lateinit var bookService: BookService

    private val book1 = BookTestModel.book1
    private val book2 = BookTestModel.book2
    private val updateBook2 = BookTestModel.updatedBook2
    private val updatedBookInfo = UpdatedBookRequest(book2.id, null, null, null, "Mars")


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
        val getBookRequest = GetBookRequest()
        every { bookRepository.findAll(any(), pageable) }.returns(pagedBooks)

        val actualPagedBook = bookService.getBooksByRequest(getBookRequest, pageable)

        verify(exactly = 1) { bookRepository.findAll(any(), pageable) }
        assertThat(actualPagedBook).isEqualTo(pagedBooks)
    }

    @Test
    @DisplayName("should get books given book request with only book title")
    fun getBooksByBookTitle() {
        val pageable: Pageable = PageRequest.of(0, 20, Sort.by(Sort.Order.asc("title")))
        val books = listOf(book1, book2)
        val pagedBooks: Page<Book> = PageImpl(books, pageable, books.size.toLong())
        val getBookRequest = GetBookRequest("the picture of Dorian Gray", null, null, null)
        every { bookRepository.findAll(any(), any()) }.returns(pagedBooks)

        val actualBooks =
            bookService.getBooksByRequest(getBookRequest, pageable)

        verify(exactly = 1) { bookRepository.findAll(any(), pageable) }
        assertThat(actualBooks).isEqualTo(pagedBooks)
    }

    @Test
    @DisplayName("should edit book given updated book info successfully")
    fun editBookInfoSuccessfully() {
        every { bookRepository.findById(any()) }.returns(Optional.of(book2))
        every { bookRepository.save(any()) }.returns(updateBook2)

        val actualUpdatedBook = bookService.editBookInfo(updatedBookInfo)

        verify(exactly = 1) { bookRepository.findById(book2.id) }
        verify(exactly = 1) { bookRepository.save(updateBook2) }
        assertThat(actualUpdatedBook).isEqualTo(updateBook2)
    }

    @Test
    @DisplayName("should failed to update book info when book is not found given book id")
    fun editBookWhenBookNotFound() {
        every { bookRepository.findById(any()) }.returns(Optional.empty())

        Assertions.assertThatThrownBy {
            bookService.editBookInfo(updatedBookInfo)
        }.isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("Book not found for book id ${updatedBookInfo.id}")
    }

    @Test
    @DisplayName("should get a book details when book is found given book id")
    fun getBookSuccessfullyGivenBookId() {
        every { bookRepository.findById(any()) }.returns(Optional.of(book2))

        val actualBook = bookService.getBookInfo(book2.id)

        verify(exactly = 1) { bookRepository.findById(book2.id) }
        assertThat(actualBook).isEqualTo(book2)
    }

    @Test
    @DisplayName("should failed to get a book details when book is not found given book id")
    fun getBookWhenBookNotFound() {
        every { bookRepository.findById(any()) }.returns(Optional.empty())

        assertThatThrownBy {
            bookService.getBookInfo(book2.id)
        }.isInstanceOf(NoSuchElementException::class.java)
            .hasMessage("Book is not found for book id ${book2.id} !")
    }

    @Test
    @DisplayName("should delete books given book ids")
    fun deleteBooksByIds() {
        every { bookRepository.deleteAllById(any()) }.returns(Unit)

        bookService.deleteByIds(listOf(book1.id, book2.id))

        verify(exactly = 1) { bookRepository.deleteAllById(listOf(book1.id, book2.id)) }
    }

    @Test
    @DisplayName("should return book list successfully order by popularity rank")
    fun getBooksOrderByPopularityRank() {
        val pageable: Pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("totalReaders")))
        val books = listOf(book2, book1)
        val pagedBooks: Page<Book> = PageImpl(books, pageable, books.size.toLong())
        every { bookRepository.findAll(pageable) }.returns(pagedBooks)

        val actualPagedBookContent = bookService.getBooksOrderByPopularityRank()

        verify(exactly = 1) { bookRepository.findAll(pageable) }
        assertThat(actualPagedBookContent).isEqualTo(pagedBooks.content.map { PopularRankBookResponseDTO(
            id = it.id,
            title = it.title,
            author = it.author,
            publisher = it.publisher,
            coverImage = it.coverImage,
            rating = it.rating,
            ratingCount = it.ratingCount
        ) })
    }

    @Test
    @DisplayName("should return book list successfully order by rating")
    fun getBooksOrderByRating() {
        val pageable: Pageable = PageRequest.of(0, 25, Sort.by(Sort.Order.desc("ratingRank")))
        val books = listOf(book2, book1)
        val pagedBooks: Page<Book> = PageImpl(books, pageable, books.size.toLong())
        val pagedRatingRankBookResponseDTO: Page<RatingRankBookResponseDTO> = pagedBooks.map {
            RatingRankBookResponseDTO(
                id = it.id,
                title = it.title,
                author = it.author,
                publisher = it.publisher,
                coverImage = it.coverImage,
                rating = it.rating,
                ratingCount = it.ratingCount
            )
        }
        every { bookRepository.findAll(pageable) }.returns(pagedBooks)

        val actualPagedBookContent = bookService.getBooksOrderByRatingRank(pageable)

        verify(exactly = 1) { bookRepository.findAll(pageable) }
        assertThat(actualPagedBookContent.content).isEqualTo(pagedRatingRankBookResponseDTO.content)
    }
}