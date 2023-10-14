package com.mm.backend.controller

import com.mm.backend.dto.book.PopularRankBookResponseDTO
import com.mm.backend.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/books")
class BookController(
    private val bookService: BookService
) {
    @GetMapping("/popularity_rank")
    fun getBooksOrderByPopularityRank(): List<PopularRankBookResponseDTO> = bookService.getBooksOrderByPopularityRank()
}