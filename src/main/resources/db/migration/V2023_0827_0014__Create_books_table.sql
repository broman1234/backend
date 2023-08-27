create table books (
    id bigint(20) primary key auto_increment not null,
    title varchar(100) not null,
    author varchar(50) not null,
    category varchar(50) not null,
    description varchar(255) default NULL
)