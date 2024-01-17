package edu.pil.springdatajpa.bootstrap;

import edu.pil.springdatajpa.domain.AuthorUuid;
import edu.pil.springdatajpa.domain.Book;
import edu.pil.springdatajpa.repositories.AuthorUuidRepository;
import edu.pil.springdatajpa.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;

    public DataInitializer(BookRepository bookRepository, AuthorUuidRepository authorUuidRepository) {
        this.bookRepository = bookRepository;
        this.authorUuidRepository = authorUuidRepository;
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

    }
}
