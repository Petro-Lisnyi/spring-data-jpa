package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Book;

import java.util.List;

public interface BookDao {
    Book getById(Long id);

    Book findBookByTitle(String title);

    Book findBookByTitleCriteria(String title);
    Book getBookByTitle_NativeQuery(String title);

    Book findByISBN(String isbn);

    Book saveBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

    List<Book> findAll();
}
