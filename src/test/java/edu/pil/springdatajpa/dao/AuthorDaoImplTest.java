package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ComponentScan(basePackages = {"edu.pil.springdatajpa.dao"})
@Import(AuthorDaoImpl.class)
public class AuthorDaoImplTest {
    @Autowired
    AuthorDao authorDao;

    @Test
    void findAllAuthorsByLastNameTest() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 5));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(5);
    }

    @Test
    void findAllAuthorsSortByFirstNameDescTest() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 5, Sort.by(Sort.Order.desc("firstName"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(5);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Yugal");
    }

    @Test
    void findAllAuthorsSortByFirstNameAscTest() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 5, Sort.by(Sort.Order.asc("firstName"))));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(5);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Ahmed");
    }

    @Test
    void findAllAuthorsByLastNameAllRecs() {
        List<Author> authors = authorDao.findAllAuthorsByLastName("Smith",
                PageRequest.of(0, 100));

        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(40);
    }
}
