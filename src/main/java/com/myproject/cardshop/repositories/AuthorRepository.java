package com.myproject.cardshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.cardshop.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer>{

}
