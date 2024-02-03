package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory entityManagerFactory;

    public AuthorDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        TypedQuery<Author> query = getEntityManager().createNamedQuery("find_author_by_name", Author.class);
        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);
        return query.getSingleResult();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        var entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        var entityManager = getEntityManager();
        entityManager.joinTransaction();
        entityManager.merge(author);
        entityManager.flush();
        entityManager.clear();

        return entityManager.find(Author.class, author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        var entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        var author = entityManager.find(Author.class, id);
        entityManager.remove(author);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Author> listAuthorsByLastName(String lastName) {
        try (var entityManager = getEntityManager()) {
            Query query = entityManager.createQuery("select a from Author a " +
                    "where a.lastName like :last_name");
            query.setParameter("last_name", lastName + "%");
            List<Author> authors = query.getResultList();
            return authors;
        }
    }

    @Override
    public List<Author> findAll() {
        try (var entityManager = getEntityManager()) {
            TypedQuery<Author> authorFindAllQuery = entityManager.createNamedQuery("author_find_all", Author.class);
            return authorFindAllQuery.getResultList();
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
