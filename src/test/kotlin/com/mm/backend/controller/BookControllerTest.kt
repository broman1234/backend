package com.mm.backend.controller

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
        val books = listOf(BookTestModel.book2, BookTestModel.book1)
        every { bookService.getBooksOrderByPopularityRank() }.returns(books)

        val result = bookController.getBooksOrderByPopularityRank()

        assertThat(result).isEqualTo(books)
    }
}