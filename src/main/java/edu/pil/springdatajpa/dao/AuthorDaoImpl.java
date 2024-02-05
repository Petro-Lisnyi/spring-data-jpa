package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;
import edu.pil.springdatajpa.repositories.AuthorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AuthorDaoImpl implements AuthorDao {
    private final AuthorRepository authorRepository;

    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.getById(id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        Author fetched = authorRepository.getById(author.getId());
        fetched.setFirstName(author.getFirstName());
        fetched.setLastName(author.getLastName());
        return authorRepository.save(fetched);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
