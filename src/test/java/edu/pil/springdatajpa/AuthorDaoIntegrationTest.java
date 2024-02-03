package edu.pil.springdatajpa;

import edu.pil.springdatajpa.dao.AuthorDao;
import edu.pil.springdatajpa.dao.AuthorDaoImpl;
import edu.pil.springdatajpa.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        var author = authorDao.getById(3L);
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
        var author = new Author();
        author.setFirstName("Tom");
        author.setLastName("Cruse");

        System.out.println("author.getId() = " + author.getId());

        var savedAuthor = authorDao.saveNewAuthor(author);
        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
        System.out.println("savedAuthor.getId() = " + savedAuthor.getId());

        authorDao.deleteAuthorById(savedAuthor.getId());
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
    }

    @Test
    void deleteAuthorTest() {
        var saved = authorDao.saveNewAuthor(new Author("tom", "cruse"));
        authorDao.deleteAuthorById(saved.getId());
        var deleted = authorDao.getById(saved.getId());
        assertThat(deleted).isNull();
    }
    @Test
    void listAuthorsByNameTest() {
        var authors = authorDao.listAuthorsByLastName("E");
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }
    @Test
    void findAuthorByNameCriteriaTest() {
        var author = authorDao.findAuthorByNameCriteria("Robert", "Martin");
        assertThat(author).isNotNull();
    }
    @Test
    void findAuthorByName_NativeQueryTest() {
        var author = authorDao.findAuthorByName_NativeQuery("Robert", "Martin");
        assertThat(author).isNotNull();
    }
    @Test
    void findAllTest() {
        var authors = authorDao.findAll();
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }
}
