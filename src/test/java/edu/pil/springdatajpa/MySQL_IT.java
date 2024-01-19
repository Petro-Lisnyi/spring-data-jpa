package edu.pil.springdatajpa;

import edu.pil.springdatajpa.domain.AuthorUuid;
import edu.pil.springdatajpa.domain.BookNatural;
import edu.pil.springdatajpa.domain.BookUuid;
import edu.pil.springdatajpa.repositories.AuthorUuidRepository;
import edu.pil.springdatajpa.repositories.BookNaturalRepository;
import edu.pil.springdatajpa.repositories.BookRepository;
import edu.pil.springdatajpa.repositories.BookUuidRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("default")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MySQL_IT {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookUuidRepository bookUuidRepository;
    @Autowired
    AuthorUuidRepository authorUuidRepository;
    @Autowired
    BookNaturalRepository bookNaturalRepository;
    @Test
    @Order(2)
    void testQuantity() {
        var quantity = bookRepository.count();
        System.out.println("quantity === " + quantity);
    }
    @Test
    void testAuthorUuid() {
        var authorUuid = new AuthorUuid();
        authorUuidRepository.save(authorUuid);
        assertThat(authorUuidRepository.getReferenceById(authorUuid.getId())).isNotNull();
    }
    @Test
    void testBookUuid() {
        var bookUuid = new BookUuid();
        bookUuidRepository.save(bookUuid);
        assertThat(bookUuidRepository.getReferenceById(bookUuid.getId())).isNotNull();
    }
    @Test
    void testBookNaturalTest() {
        var bookNatural = new BookNatural();
        bookNatural.setTitle("My new hobby");
        bookNaturalRepository.save(bookNatural);
        assertThat(bookNaturalRepository.getReferenceById(bookNatural.getTitle())).isNotNull();
    }
}
