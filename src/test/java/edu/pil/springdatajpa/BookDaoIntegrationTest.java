package edu.pil.springdatajpa;

import edu.pil.springdatajpa.dao.BookDao;
import edu.pil.springdatajpa.domain.Author;
import edu.pil.springdatajpa.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
//@Import({BookDaoSpring.class})
@ComponentScan(basePackages = {"edu.pil.springdatajpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Qualifier("bookDaoSpring")
    @Autowired
    BookDao bookDao;

    @Test
    void getBookTest() {
        var book = bookDao.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    void getBookByTitleTest() {
        var book = bookDao.findBookByTitle("Domain-Driven Design");
        System.out.println("book_id = " + book.getId());
        assertThat(book).isNotNull();
    }

    @Test
    void saveBookTest() {
        var book = new Book("Harnessing Hibernate",
                "978-0-596-51772-4", "James Elliot, Tim O'Brien");
        var author = new Author("James", "Elliot");
        var savedBook = bookDao.saveBook(book);
        assertThat(savedBook).isNotNull();
//        //cleanup db
//        if (savedBook != null)
//            bookDao.deleteBookById(savedBook.getId());
//        if (savedAuthor != null)
//            authorDao.deleteAuthorById(savedAuthor.getId());

    }

    @Test
    void updateBookTest() {
        var book = new Book("English For Tech", "anna_gandrabura", "Anna Gandrabura");
        var publisher = book.getPublisher();
        var savedBook = bookDao.saveBook(book);
        savedBook.setIsbn("annglish_");

        var updatedBook = bookDao.updateBook(savedBook);
        assertThat(updatedBook.getIsbn()).isEqualTo("annglish_");
//        bookDao.deleteBookById(updatedBook.getId());
    }

    @Test
    void deleteBookTest() {
        var saved = bookDao.saveBook(new Book());
        bookDao.deleteBookById(saved.getId());

        assertThrows(JpaObjectRetrievalFailureException.class,
                () -> bookDao.getById(saved.getId()));
    }

    @Test
    void findAllBooksTest() {
        List<Book> books = bookDao.findAllBooks();
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(2);
    }

    @Test
    void findAllBooksPage1Test() {
        List<Book> books = bookDao.findAllBooks(2, 0);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void findAllBooksPage2Test() {
        List<Book> books = bookDao.findAllBooks(2, 2);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void findAllBooksPage10Test() {
        List<Book> books = bookDao.findAllBooks(2, 18)  ;
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void findAllBooksPage1_pageableTest() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 2));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void findAllBooksPage2_pageableTest() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(1, 2));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void findAllBooksPage10_pageableTest() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(9, 2))  ;
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void findAllBooksPage1_SortByTitleTest() {
        List<Book> books = bookDao.findAllBooksSortByTitle(PageRequest.of(0, 4,
                Sort.by(Sort.Order.desc("title"))))  ;
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(4);
    }
}
