package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Book;
import edu.pil.springdatajpa.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class BookDaoImpl implements BookDao {

    private final BookRepository bookRepository;

    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.getById(id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Book fetched = bookRepository.getById(book.getId());
        fetched.setAuthorId(book.getAuthorId());
        fetched.setIsbn(book.getIsbn());
        fetched.setPublisher(book.getPublisher());
        fetched.setTitle(book.getTitle());
        return bookRepository.save(fetched);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}