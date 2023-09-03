package com.mm.backend.controller

import com.mm.backend.models.Book
import com.mm.backend.service.BookService
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
    @DisplayName("should return book successfully when adding a book")
    fun addBook() {
        val expectedBook = Book(
            title = "the picture of Dorian Gray",
            author = "Oscar Wilde",
            category = "fiction",
            publisher = "Galaxy",
            description = "a famous Irish writer and playwright. " +
                    "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                    "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                    "and the corrupting influence of art and beauty"
        )
        every { bookService.addBook(any()) }.returns(expectedBook)

        val returnedBook = bookController.addBook(expectedBook)

        assertThat(returnedBook).isEqualTo(expectedBook)
    }
}