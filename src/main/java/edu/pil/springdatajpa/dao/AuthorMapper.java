package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;
import edu.pil.springdatajpa.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        rs.next();
        var author = new Author();
        author.setId(rs.getLong("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setLastName(rs.getString("last_name"));

        author.setBooks(new ArrayList<>());
        if (rs.getString("isbn") != null)
            do {
                author.getBooks().add(mapBook(rs));
            }
            while (rs.next());

        return author;
    }

    private Book mapBook(ResultSet rs) throws SQLException {
        var book = new Book();
        book.setId(rs.getLong(4));
        book.setIsbn(rs.getString(5));
        book.setPublisher(rs.getString(6));
        book.setTitle(rs.getString(7));
        book.setAuthorId(rs.getLong(1));
        return book;
    }
}
