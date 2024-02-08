package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Book;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"edu.pil.springdatajpa.dao"})
public class BookDaoHibernateTest {
    @Autowired
    EntityManagerFactory emf;

    BookDao bookDao;

    @BeforeEach
    void setUp() {
        bookDao = new BookDaoHibernate(emf);
    }

    @Test
    void findAllBooks() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 5));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
        assertThat(books.get(0).getTitle()).isEqualTo("Clean Code");
    }

    @Test
    void findAllBooksSortByTitle() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 5,
                Sort.by(Sort.Order.desc("title"))));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
    }
}
