package edu.pil.springdatajpa;

import edu.pil.springdatajpa.dao.AuthorDao;
import edu.pil.springdatajpa.dao.AuthorDaoImpl;
import edu.pil.springdatajpa.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@Import(AuthorDaoImpl.class)
//@ComponentScan(basePackages = {"edu.pil.springdatajpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void getAuthorTest() {
        var author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void getAuthorByNameTest() {
        var author = authorDao.findAuthorByName("Robert", "Martin");
        System.out.println("author_id = " + author.getId());
        assertThat(author).isNotNull();
    }

    @Test
    void saveAuthorTest() {
        var savedAuthor = new Author();
        savedAuthor.setFirstName("Tom");
        savedAuthor.setLastName("Cruse");

        System.out.println("savedAuthor.getId() = " + savedAuthor.getId());

        var fetchedAuthor = authorDao.saveNewAuthor(savedAuthor);
        assertThat(fetchedAuthor).isNotNull();
        System.out.println("fetchedAuthor.getId() = " + fetchedAuthor.getId());
//        authorDao.deleteAuthorById(fetchedAuthor.getId());
    }

    @Test
    void updateAuthorTest() {
        var author = new Author();
        author.setFirstName("tom");
        author.setLastName("C");

        var saved = authorDao.saveNewAuthor(author);
        saved.setLastName("cruse");

        var updated = authorDao.updateAuthor(saved);
        assertThat(updated.getLastName()).isEqualTo("cruse");
        authorDao.deleteAuthorById(updated.getId());
    }

    @Test
    void deleteAuthorTest() {
        var saved = authorDao.saveNewAuthor(new Author("tom", "cruse"));
        authorDao.deleteAuthorById(saved.getId());
        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getById(saved.getId()));
    }
}
