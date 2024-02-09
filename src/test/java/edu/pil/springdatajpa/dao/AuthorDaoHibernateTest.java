package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;
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
public class AuthorDaoHibernateTest {
    @Autowired
    EntityManagerFactory emf;

    AuthorDao authorDao;

    @BeforeEach
    void setUp() {
        authorDao = new AuthorDaoHibernateImpl(emf);
    }

    @Test
    void findAllAuthorsTest() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith", PageRequest.of(0, 5));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(5);
    }

    @Test
    void findAllAuthorsSortByFirstNameTest() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 5, Sort.by(Sort.Order.desc("firstname"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(5);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Yugal");
    }
}
