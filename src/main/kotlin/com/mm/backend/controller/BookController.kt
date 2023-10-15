package com.mm.backend.controller

import com.mm.backend.dto.book.PopularRankBookResponseDTO
import com.mm.backend.dto.book.RatingRankBookResponseDTO
import com.mm.backend.service.BookService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
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

    @GetMapping("/rating_rank")
    fun getBooksOrderByRatingRank(@PageableDefault(
        page = 0,
        size = 25,
        sort = ["ratingRank"],
        direction = Sort.Direction.DESC
    ) pageable: Pageable): Page<RatingRankBookResponseDTO> =
        bookService.getBooksOrderByRatingRank(pageable)
}