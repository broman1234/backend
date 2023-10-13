package com.mm.backend.testmodels

import com.mm.backend.enums.Category
import com.mm.backend.models.Book


object BookTestModel {

    val book1 = Book(
        id = 1,
        title = "the picture of Dorian Gray",
        author = "Oscar Wilde",
        category = Category.FICTION,
        publisher = "Galaxy",
        description = "a famous Irish writer and playwright. " +
                "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                "and the corrupting influence of art and beauty",
        wantToReadCount = 2,
        haveReadCount = 1
    )

    val book2 = Book(
        id = 2,
        title = "the picture of Dorian Gray",
        author = "Oscar Wilde",
        category = Category.FICTION,
        publisher = "Earth",
        description = "a famous Irish writer and playwright. " +
                "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                "and the corrupting influence of art and beauty",
        wantToReadCount = 2,
        haveReadCount = 2
    )

    val updatedBook2 = Book(
        id = 2,
        title = "the picture of Dorian Gray",
        author = "Oscar Wilde",
        category = Category.FICTION,
        publisher = "Mars",
        description = "a famous Irish writer and playwright. " +
                "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                "and the corrupting influence of art and beauty",
        wantToReadCount = 2,
        haveReadCount = 2
    )
}