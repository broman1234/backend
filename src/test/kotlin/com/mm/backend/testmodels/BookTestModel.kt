package com.mm.backend.testmodels

import com.mm.backend.enums.Category
import com.mm.backend.models.Book
import java.time.Instant


object BookTestModel {

    val book1 = Book(
        id = 1,
        title = "the picture of Dorian Gray",
        author = "Oscar Wilde",
        category = Category.FICTION,
        publisher = "Galaxy",
        coverImage = "",
        description = "a famous Irish writer and playwright. " +
                "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                "and the corrupting influence of art and",
        wantToReadCount = 2,
        haveReadCount = 1,
        rating = 8,
        ratingCount = 11
    )

    val book2 = Book(
        id = 2,
        title = "the picture of Dorian Gray",
        author = "Oscar Wilde",
        category = Category.FICTION,
        publisher = "Earth",
        coverImage = "",
        description = "a famous Irish writer and playwright. " +
                "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                "and the corrupting influence of art and",
        wantToReadCount = 2,
        haveReadCount = 2,
        rating = 9,
        ratingCount = 10
    )

    val updatedBook2 = Book(
        id = 2,
        title = "the picture of Dorian Gray",
        author = "Oscar Wilde",
        category = Category.FICTION,
        publisher = "Mars",
        coverImage = "",
        description = "a famous Irish writer and playwright. " +
                "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                "and the corrupting influence of art and",
        wantToReadCount = 2,
        haveReadCount = 2,
        rating = 9,
        ratingCount = 10
    )
}