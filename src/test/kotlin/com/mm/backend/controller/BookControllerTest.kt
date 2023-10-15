package com.mm.backend.controller

import com.mm.backend.dto.book.PopularRankBookResponseDTO
import com.mm.backend.dto.book.RatingRankBookResponseDTO
import com.mm.backend.models.Book
import com.mm.backend.service.BookService
import com.mm.backend.testmodels.BookTestModel
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
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

    @Test
    @DisplayName("should return book list successfully order by popularity rank")
    fun getBooksOrderByPopularityRank() {
        val books = listOf(BookTestModel.book2, BookTestModel.book1).map {
            PopularRankBookResponseDTO(
                id = it.id,
                title = it.title,
                author = it.author,
                publisher = it.publisher,
                coverImage = it.coverImage,
                rating = it.rating,
                ratingCount = it.ratingCount
            )
        }
        every { bookService.getBooksOrderByPopularityRank() }.returns(books)

        val result = bookController.getBooksOrderByPopularityRank()

        assertThat(result).isEqualTo(books)
    }

    @Test
    @DisplayName("should return book list successfully order by rating rank")
    fun getBooksOrderByRatingRank() {
        val pageable: Pageable = PageRequest.of(0, 25, Sort.by(Sort.Order.desc("rating")))
        val books = listOf(BookTestModel.book2, BookTestModel.book1).map {
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
        val pagedBooks: Page<RatingRankBookResponseDTO> = PageImpl(books, pageable, books.size.toLong())
        every { bookService.getBooksOrderByRatingRank(pageable) }.returns(pagedBooks)

        val result = bookController.getBooksOrderByRatingRank(pageable)

        assertThat(result.content).isEqualTo(pagedBooks.content)
    }
}