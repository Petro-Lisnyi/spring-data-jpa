package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Book;
import edu.pil.springdatajpa.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoSpring implements BookDao {

    private final BookRepository bookRepository;

    public BookDaoSpring(BookRepository bookRepository) {
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

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        Pageable pageable = PageRequest.ofSize(pageSize);

        if (offset > 0) {
            pageable = pageable.withPage(offset / pageSize);
        } else {
            pageable = pageable.withPage(0);
        }

        return this.findAllBooks(pageable);
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        return null;
    }
}
