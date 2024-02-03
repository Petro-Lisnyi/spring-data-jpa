package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;
import edu.pil.springdatajpa.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class BookDaoImpl implements BookDao {

    private final EntityManagerFactory entityManagerFactory;

    public BookDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Book getById(Long id) {
        return getEntityManager().find(Book.class, id);
    }

    @Override
    public Book findBookByTitle(String title) {
        TypedQuery<Book> query = getEntityManager().createNamedQuery("book_find_by_title", Book.class);
        query.setParameter("title", title);
        return query.getSingleResult();
    }

    @Override
    public Book findBookByTitleCriteria(String title) {
        try (EntityManager entityManager = getEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

            Root<Book> bookRoot = criteriaQuery.from(Book.class);

            ParameterExpression<String> titleParam = criteriaBuilder.parameter(String.class);

            Predicate titlePredicate = criteriaBuilder.equal(bookRoot.get("title"), titleParam);

            criteriaQuery.select(bookRoot).where(criteriaBuilder.and(titlePredicate, titlePredicate));

            TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
            query.setParameter(titleParam, title);
            return query.getSingleResult();
        }
    }

    @Override
    public Book findByISBN(String isbn) {
        try (var entityManager = getEntityManager()) {
            var typedQuery = entityManager.createQuery("select b from Book b where b.isbn = :isbn", Book.class);
            typedQuery.setParameter("isbn", isbn);
            return typedQuery.getSingleResult();
        }
    }

    @Override
    public Book saveBook(Book book) {
        var entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        var entityManager = getEntityManager();
        entityManager.joinTransaction();
        entityManager.merge(book);
        entityManager.flush();
        entityManager.clear();

        return entityManager.find(Book.class, book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        var entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        var book = entityManager.find(Book.class, id);
        entityManager.remove(book);
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    @Override
    public List<Book> findAll() {
        try (var entityManager = getEntityManager()) {
            return entityManager.createNamedQuery("book_find_all", Book.class).getResultList();
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
