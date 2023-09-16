package com.mm.backend.testmodels

import com.mm.backend.models.Book


object BookTestModel {

    val book1 = Book(
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
        title = "In Search of Lost Time",
        author = "Proust",
        category = "fiction",
        publisher = "Galaxy",
        description = "this novel is celebrated for its profound exploration of themes like " +
                "love, art, social class, time, and self-awareness."
    )
}