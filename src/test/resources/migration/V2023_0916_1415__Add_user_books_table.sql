create table user_books
(
    id      bigint primary key auto_increment not null,
    user_id bigint,
    book_id bigint
)
