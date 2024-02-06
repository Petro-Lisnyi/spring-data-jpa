package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?", getBookMapper(), id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE title = ?", getBookMapper(), title);
    }

    @Override
    public Book saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId());
        var last_insert_id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return this.getById(last_insert_id);
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update("UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId(), book.getId());
        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    @Override
    public List<Book> findAllBooks() {
        return jdbcTemplate.query("SELECT * FROM book", getBookMapper());
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return jdbcTemplate.query("SELECT * FROM book limit ? offset ?",
                getBookMapper(), pageSize, offset);
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return jdbcTemplate.query("SELECT * FROM book limit ? offset ?",
                getBookMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    private RowMapper<Book> getBookMapper() {
        return new BookMapper();
    }
}
