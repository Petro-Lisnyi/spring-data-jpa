package edu.pil.springdatajpa;

import edu.pil.springdatajpa.dao.AuthorDao;
import edu.pil.springdatajpa.dao.AuthorDaoImpl;
import edu.pil.springdatajpa.dao.BookDao;
import edu.pil.springdatajpa.dao.BookDaoImpl;
import edu.pil.springdatajpa.domain.Author;
import edu.pil.springdatajpa.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class})
//@ComponentScan(basePackages = {"edu.pil.springdatajpa.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;
    @Autowired
    AuthorDao authorDao;

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
        var savedAuthor = authorDao.saveNewAuthor(author);
        if (savedAuthor != null)
            book.setAuthorId(savedAuthor.getId());
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
        var savedAuthor = authorDao.saveNewAuthor(new Author(publisher.split(" ")[0], publisher.split(" ")[1]));
        if(savedAuthor != null)
            book.setAuthorId(savedAuthor.getId());
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

        assertThrows(EmptyResultDataAccessException.class,
                () -> bookDao.getById(saved.getId()));
    }

    @Test
    void findAllBooksTest() {
        List<Book> books = bookDao.findAllBooks();
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(2);
    }
}
