package edu.pil.springdatajpa.domain.composite;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Data
@Entity
@IdClass(NameId.class)
public class AuthorComposite {
    @Id
    private String firstName;
    @Id
    private String lastName;
    private String country;
}


