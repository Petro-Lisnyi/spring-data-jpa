package edu.pil.springdatajpa.dao;

import edu.pil.springdatajpa.domain.Author;

public interface AuthorDao {
    Author  getById(Long id);
}
