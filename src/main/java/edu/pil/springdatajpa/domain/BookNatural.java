package edu.pil.springdatajpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BookNatural {
    @Id
    private String title;
    private String publisher;
    private String isbn;
}
