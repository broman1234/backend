package com.mm.backend.exception

class BookAlreadyExistsException(override val message: String?) : RuntimeException(message)