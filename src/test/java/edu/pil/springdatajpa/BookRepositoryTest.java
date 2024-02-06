package edu.pil.springdatajpa;

import edu.pil.springdatajpa.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"edu.pil.springdatajpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

     @Autowired
    BookRepository bookRepository;

    @Test
    void testEmptyResultException() {
        assertThrows(EmptyResultDataAccessException.class, () -> bookRepository.readByTitle("Some title"));
    }

    @Test
    void testNullParam() {
        assertNull(bookRepository.getByTitle(null));
    }

    @Test
    void testNoException() {
        assertNull(bookRepository.getByTitle("Some"));
    }

}
