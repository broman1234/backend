package com.mm.backend.repository

import com.mm.backend.models.Book
import com.mm.backend.testmodels.BookTestModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@DataJpaTest
@EnableJpaAuditing
internal class BookRepositoryTest {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Test
    @DisplayName("should return paged books")
    fun findPagedBooks() {
        val books = listOf(BookTestModel.book2, BookTestModel.book1)
        val pagedBooks: Page<Book> = PageImpl(books, PageRequest.of(0, 10, Sort.by(Sort.Order.desc("totalReaders"))), books.size.toLong())
        bookRepository.save(BookTestModel.book1)
        bookRepository.save(BookTestModel.book2)

        val result = bookRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("totalReaders"))))

        assertThat(result.content).isEqualTo(pagedBooks.content)
    }
}