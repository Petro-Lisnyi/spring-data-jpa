package edu.pil.springdatajpa.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@NamedQuery(name = "Book.jpaNamed", query = "from Book b where b.title = :title")
//@NamedQueries({
//        @NamedQuery(name = "book_find_all", query = "from Book"),
//        @NamedQuery(name = "book_find_by_title", query = "select a from Book a where a.title = :title")
//})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private String publisher;
    private Long authorId;

    public Book(String title, String isbn, String publisher) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
    }
}
