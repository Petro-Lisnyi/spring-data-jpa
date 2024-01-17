package edu.pil.springdatajpa.repositories;

import edu.pil.springdatajpa.domain.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
