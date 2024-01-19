package edu.pil.springdatajpa.repositories;

import edu.pil.springdatajpa.domain.AuthorUuid;
import edu.pil.springdatajpa.domain.composite.AuthorComposite;
import edu.pil.springdatajpa.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}
