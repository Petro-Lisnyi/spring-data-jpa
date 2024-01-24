package edu.pil.springdatajpa.repositories;


import edu.pil.springdatajpa.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
