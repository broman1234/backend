package com.mm.backend.dto

data class BookRequest(
    private val title: String? = null,
    private val author: String? = null,
    private val category: String? = null,
    private val publisher: String? = null,
)