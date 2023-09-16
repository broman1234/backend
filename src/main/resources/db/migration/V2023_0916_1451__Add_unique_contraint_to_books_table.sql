ALTER TABLE books
    ADD CONSTRAINT unique_book UNIQUE (title, author, publisher);