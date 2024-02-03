package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

    List<Author> listAuthorsByLastName(String lastName);

    List<Author> findAll();

    Author findAuthorByNameCriteria(String firstName, String lastName);
}
