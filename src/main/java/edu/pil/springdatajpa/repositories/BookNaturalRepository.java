package edu.pil.springdatajpa.repositories;

import edu.pil.springdatajpa.domain.Book;
import edu.pil.springdatajpa.domain.BookNatural;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
}
