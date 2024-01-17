package edu.pil.springdatajpa.bootstrap;

import edu.pil.springdatajpa.domain.AuthorUuid;
import edu.pil.springdatajpa.domain.Book;
import edu.pil.springdatajpa.domain.BookUuid;
import edu.pil.springdatajpa.repositories.AuthorUuidRepository;
import edu.pil.springdatajpa.repositories.BookRepository;
import edu.pil.springdatajpa.repositories.BookUuidRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;

    public DataInitializer(BookRepository bookRepository, AuthorUuidRepository authorUuidRepository, BookUuidRepository bookUuidRepository) {
        this.bookRepository = bookRepository;
        this.authorUuidRepository = authorUuidRepository;
        this.bookUuidRepository = bookUuidRepository;
    }

    @Override
    public void run(String... args) throws Exception {

//        bookRepository.deleteAll();
        var bookD = new Book("Design Patterns 2", "123452", "$ome1Else", 11L);
        System.out.println("Id: " + bookD.getId());

        var bookSaved = bookRepository.save(bookD);
        System.out.println("Id: " + bookSaved.getId());

        var bookS = new Book("Spring in action", "12345678", "Craig", 2L);
        var saveS = bookRepository.save(bookS);

        bookRepository.findAll().forEach(book -> {
            System.out.println(book.getId());
            System.out.println(book.getTitle());
        });

        AuthorUuid authorUuid = new AuthorUuid();
        authorUuid.setFirstName("Joe");
        authorUuid.setLastName("Buck");
        AuthorUuid savedAuthor = authorUuidRepository.save(authorUuid);
        System.out.println("Saved Author UUID: " + savedAuthor.getId() );

        BookUuid bookUuid = new BookUuid();
        bookUuid.setTitle("All About UUIDs");
        BookUuid savedBookUuid = bookUuidRepository.save(bookUuid);
        System.out.println("Saved Book UUID: " + savedBookUuid.getId());
    }
}
