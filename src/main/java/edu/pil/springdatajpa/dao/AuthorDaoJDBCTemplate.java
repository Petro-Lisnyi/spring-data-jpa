package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AuthorDaoJDBCTemplate implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    @Override
    public List<Author> findAllAuthorsByLastName(String lastName, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from author where last_name = ? ");

        if (pageable.getSort().getOrderFor("firstname") != null)
            sb.append("order by first_name " +
                    pageable.getSort().getOrderFor("firstname").getDirection().name() + " ");

        sb.append("limit ? offset ?");

        System.out.println(sb + "limit " + pageable.getPageSize() + " offset " + pageable.getOffset());
        return jdbcTemplate.query(sb.toString(),
                getAuthorMapper(), lastName, pageable.getPageSize(), pageable.getOffset());
    }

    private AuthorMapper getAuthorMapper() {
        return new AuthorMapper();
    }
}
