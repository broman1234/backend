package com.mm.backend.service

import com.mm.backend.exception.BookAlreadyExistsException
import com.mm.backend.models.Book
import com.mm.backend.repository.BookRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class BookService(
    private val bookRepository: BookRepository
) {

    @Transactional
    fun addBook(book: Book) {
        try {
            bookRepository.save(book)
        } catch (e: DataIntegrityViolationException) {
            throw BookAlreadyExistsException(e.message)
        }
    }

    fun getBooks(): List<Book> = bookRepository.findAll()
}