drop table if exists books;
drop table if exists books_seq;
create table books_seq (
        next_val bigint
    ) engine=InnoDB;
create table books (
        id bigint not null,
        isbn varchar(255),
        publisher varchar(255),
        title varchar(255),
        primary key (id)
    ) engine=InnoDB;

insert into books_seq values ( 1 );