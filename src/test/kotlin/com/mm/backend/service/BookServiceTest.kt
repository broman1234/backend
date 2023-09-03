package com.mm.backend.service

import com.mm.backend.models.Book
import com.mm.backend.repository.BookRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.math.exp

@ExtendWith(MockKExtension::class)
@DisplayName("BookServiceTest")
internal class BookServiceTest {

    @MockK
    private lateinit var bookRepository: BookRepository

    @InjectMockKs
    private lateinit var bookService: BookService

    @Test
    @DisplayName("should return added book when adding a new book")
    fun addBook() {
        val expectedBook = Book(
            title = "the picture of Dorian Gray",
            author = "Oscar Wilde",
            category = "fiction",
            description = "a famous Irish writer and playwright. " +
                    "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                    "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                    "and the corrupting influence of art and beauty"
        )
        every { bookRepository.save(any()) }.returns(expectedBook)

        val returnedBook = bookService.addBook(expectedBook)

        assertThat(returnedBook).isEqualTo(expectedBook)
    }
}