package edu.pil.springdatajpa;

import edu.pil.springdatajpa.domain.Book;
import edu.pil.springdatajpa.repositories.BookRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testBookStream() {
        AtomicInteger count = new AtomicInteger();
        bookRepository.findAllByTitleNotNull()
                .forEach(book -> count.incrementAndGet());

        assertThat(count.get()).isGreaterThan(4);
    }

    @Test
    @SneakyThrows
    void testBookFuture() {
        Future<Book> bookFuture = bookRepository.queryByTitle("Clean Code");

        var book = bookFuture.get();

        assertNotNull(book);
    }

    @Test
    void testBookQuery() {
        var book = bookRepository.findBookByTitleWithQuery("Clean Code");

        assertNotNull(book);
    }
}
