package com.mm.backend.service

import com.mm.backend.dto.BookRequest
import com.mm.backend.dto.BookSpecifications
import com.mm.backend.dto.UpdatedBookRequest
import com.mm.backend.exception.BookAlreadyExistsException
import com.mm.backend.models.Book
import com.mm.backend.repository.BookRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
import javax.transaction.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository
) {

    @Transactional
    fun addBook(book: Book): Book {
        try {
            return bookRepository.save(book)
        } catch (e: DataIntegrityViolationException) {
            throw BookAlreadyExistsException(e.message)
        }
    }
    fun getBooksByRequest(bookRequest: BookRequest, pageable: Pageable): Page<Book> {
        return bookRepository.findAll(BookSpecifications.withRequest(bookRequest), pageable)
    }

    fun editBookInfo(updatedBook: UpdatedBookRequest): Book {
        return bookRepository.findById(updatedBook.id)
            .orElseThrow { NoSuchElementException("Book not found for book id ${updatedBook.id}") }
            .apply {
                title = updatedBook.title.takeIf { !it.isNullOrBlank() } ?: title
                author = updatedBook.author.takeIf { !it.isNullOrBlank() } ?: author
                category = updatedBook.category.takeIf { !it.isNullOrBlank() } ?: category
                publisher = updatedBook.publisher.takeIf { !it.isNullOrBlank() } ?: publisher
            }
            .let { bookRepository.save(it) }
    }
}