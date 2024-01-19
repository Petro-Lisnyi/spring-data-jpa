package edu.pil.springdatajpa;

import edu.pil.springdatajpa.domain.AuthorUuid;
import edu.pil.springdatajpa.domain.BookNatural;
import edu.pil.springdatajpa.domain.BookUuid;
import edu.pil.springdatajpa.domain.composite.AuthorComposite;
import edu.pil.springdatajpa.domain.composite.AuthorEmbedded;
import edu.pil.springdatajpa.domain.composite.NameId;
import edu.pil.springdatajpa.repositories.*;
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
    @Autowired
    AuthorCompositeRepository authorCompositeRepository;
    @Autowired
    AuthorEmbeddedRepository authorEmbeddedRepository;

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
    void bookNaturalTest() {
        var bookNatural = new BookNatural();
        bookNatural.setTitle("My new hobby");
        bookNaturalRepository.save(bookNatural);
        assertThat(bookNaturalRepository.getReferenceById(bookNatural.getTitle())).isNotNull();
    }

    @Test
    void authorCompositeTest() {
        var nameId = new NameId("John", "Wall");

        var authorComposite = new AuthorComposite();
        authorComposite.setFirstName(nameId.getFirstName());
        authorComposite.setLastName(nameId.getLastName());
        authorComposite.setCountry("UK");
        var saved = authorCompositeRepository.save(authorComposite);
        assertThat(saved).isNotNull();
        var fetched = authorCompositeRepository.getReferenceById(nameId);
        assertThat(fetched).isNotNull();
    }
    @Test
    void authorEmbeddedTest() {
        var nameId = new NameId("John", "Wall");
        var authorComposite = new AuthorEmbedded(nameId);
        authorComposite.setCountry("UK");
        var saved = authorEmbeddedRepository.save(authorComposite);
        assertThat(saved).isNotNull();
        var fetched = authorEmbeddedRepository.getReferenceById(nameId);
        assertThat(fetched).isNotNull();
    }
}
