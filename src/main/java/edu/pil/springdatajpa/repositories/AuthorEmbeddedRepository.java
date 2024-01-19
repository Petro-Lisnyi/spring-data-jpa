package edu.pil.springdatajpa.repositories;

import edu.pil.springdatajpa.domain.composite.AuthorComposite;
import edu.pil.springdatajpa.domain.composite.AuthorEmbedded;
import edu.pil.springdatajpa.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
