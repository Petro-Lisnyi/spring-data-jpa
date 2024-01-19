package edu.pil.springdatajpa.domain.composite;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "author_composite")
public class AuthorEmbedded {
    @EmbeddedId
    private NameId nameId;
    private String country;

    public AuthorEmbedded(NameId nameId) {
        this.nameId = nameId;
    }
}


