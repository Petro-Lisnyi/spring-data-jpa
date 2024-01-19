package edu.pil.springdatajpa.domain.composite;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameId implements Serializable {
    private String firstName;
    private String lastName;


}
