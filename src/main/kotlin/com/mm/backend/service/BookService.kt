package com.mm.backend.service

import com.mm.backend.dto.BookRequest
import com.mm.backend.exception.BookAlreadyExistsException
import com.mm.backend.models.Book
import com.mm.backend.repository.BookRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
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

    fun getBooks(pageable: Pageable): Page<Book> = bookRepository.findAll(pageable)

    fun getBooksByRequest(bookRequest: BookRequest, pageable: Pageable): Page<Book> {
        return bookRepository.findAllByRequest(bookRequest, pageable)
    }
}