package com.mm.backend.dto.book

data class PopularRankBookResponseDTO(
    val id: Long,
    val title: String,
    val author: String,
    val publisher: String,
    val coverImage: String,
    val rating: Int?
)