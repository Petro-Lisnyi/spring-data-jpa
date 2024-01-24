package edu.pil.springdatajpa.repositories;


import edu.pil.springdatajpa.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
