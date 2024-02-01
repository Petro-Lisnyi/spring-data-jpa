package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;
import edu.pil.springdatajpa.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;


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
        TypedQuery<Book> query = getEntityManager().createQuery("select a from Book a " +
                "where a.title = :title", Book.class);
        query.setParameter("title", title);
        return query.getSingleResult();
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

    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
