package edu.pil.springdatajpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
//@NamedQueries({
//        @NamedQuery(name = "author_find_all", query = "from Author"),
//        @NamedQuery(name = "find_author_by_name",
//                query = "select a from Author a where a.firstName = :first_name and a.lastName = :last_name")
//})
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
