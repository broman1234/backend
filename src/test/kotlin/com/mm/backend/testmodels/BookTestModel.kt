package com.mm.backend.testmodels

import com.mm.backend.models.Book


object BookTestModel {

    val book1 = Book(
        id = 1,
        title = "the picture of Dorian Gray",
        author = "Oscar Wilde",
        category = "fiction",
        publisher = "Galaxy",
        description = "a famous Irish writer and playwright. " +
                "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                "and the corrupting influence of art and beauty"
    )

    val book2 = Book(
        id = 2,
        title = "the picture of Dorian Gray",
        author = "Oscar Wilde",
        category = "fiction",
        publisher = "Earth",
        description = "a famous Irish writer and playwright. " +
                "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                "and the corrupting influence of art and beauty"
    )

    val updatedBook2 = Book(
        id = 2,
        title = "the picture of Dorian Gray",
        author = "Oscar Wilde",
        category = "fiction",
        publisher = "Mars",
        description = "a famous Irish writer and playwright. " +
                "It was first published in 1890 and is considered one of Wilde's most significant works. " +
                "The novel is a Gothic and philosophical tale that explores themes of morality, vanity, " +
                "and the corrupting influence of art and beauty"
    )
}