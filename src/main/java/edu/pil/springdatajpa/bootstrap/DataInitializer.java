package edu.pil.springdatajpa.bootstrap;

import edu.pil.springdatajpa.domain.Book;
import edu.pil.springdatajpa.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        var bookD = new Book("Design Patterns 2", "`123452", "$ome1Else");
        System.out.println("Id: " + bookD.getId());

        var bookSaved = bookRepository.save(bookD);
        System.out.println("Id: " + bookSaved.getId());

        var bookS = new Book("Spring in action", "12345678", "Craig");
        var saveS = bookRepository.save(bookS);

        bookRepository.findAll().forEach(book -> {
            System.out.println(book.getId());
            System.out.println(book.getTitle());
        });

    }
}
