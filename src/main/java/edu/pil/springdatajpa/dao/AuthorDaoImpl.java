package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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

    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        try (EntityManager entityManager = getEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);

            Root<Author> authorRoot = criteriaQuery.from(Author.class);

            ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

            Predicate firstNamePredicate = criteriaBuilder.equal(authorRoot.get("firstName"), firstNameParam);
            Predicate lastNamePredicate = criteriaBuilder.equal(authorRoot.get("lastName"), lastNameParam);

            criteriaQuery.select(authorRoot).where(criteriaBuilder.and(firstNamePredicate, lastNamePredicate));

            TypedQuery<Author> query = entityManager.createQuery(criteriaQuery);
            query.setParameter(firstNameParam, firstName);
            query.setParameter(lastNameParam, lastName);
            return query.getSingleResult();
        }
    }

    @Override
    public Author findAuthorByName_NativeQuery(String firstName, String lastName) {
        try (EntityManager entityManager = getEntityManager()) {
            Query nativeQuery = entityManager.createNativeQuery("select * from author a " +
                    "where a.first_name = ? and  a.last_name = ?", Author.class);
            nativeQuery.setParameter(1, firstName);
            nativeQuery.setParameter(2, lastName);
            return (Author) nativeQuery.getSingleResult();
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
