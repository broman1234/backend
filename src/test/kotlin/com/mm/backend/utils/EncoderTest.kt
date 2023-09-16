package com.mm.backend.utils

import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


class EncoderTest {
    @Test
    fun testEncode() {
        val encoder = BCryptPasswordEncoder()
        val result = encoder.encode("1234")
        println(result)
    }
}