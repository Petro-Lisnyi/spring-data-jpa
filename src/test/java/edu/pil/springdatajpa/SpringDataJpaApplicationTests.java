package edu.pil.springdatajpa;

import edu.pil.springdatajpa.domain.Book;
import edu.pil.springdatajpa.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ComponentScan(basePackages = {"edu.pil.springdatajpa.bootstrap"}) to run that
//@SpringBootTest
class SpringDataJpaApplicationTests {
    @Autowired
    BookRepository bookRepository;

    @Test
//    @Rollback(value = false) the same as @Commit
    @Commit
    @Order(1)
    void testJpaTestSplice() {
        var quantityBefore = bookRepository.count();
        System.out.println("======================> quantityBefore -> " + quantityBefore);
        bookRepository.save(new Book("My new book2", "123456", "John Thomson2"));
        var quantityAfter = bookRepository.count();
        System.out.println("======================> quantityAfter -> " + quantityAfter);
        assertThat(quantityBefore).isLessThan(quantityAfter);
    }

    @Test
    @Order(2)
    void testQuantity() {
        var quantity = bookRepository.count();
        assertThat(quantity).isEqualTo(15);
    }

}
