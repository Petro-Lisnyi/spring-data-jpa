package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;

public interface AuthorDao {
    Author  getById(Long id);
    Author findAuthorByName(String firstName, String lastName);

    Author saveAuthor(Author author);

    Author updateAuthor(Author saved);

    void deleteAuthorById(Long id);
}
