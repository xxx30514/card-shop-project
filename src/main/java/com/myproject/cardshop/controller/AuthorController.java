package com.myproject.cardshop.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.cardshop.model.Author;

@RestController
@RequestMapping("/authors")
public class AuthorController {
	
	@GetMapping
	public List<Author> findAuthors() {
		return List.of(new Author("test", "test2", "email", 30, null, null, null),new Author("test", "test2", "email5", 35, null, null, null));
	}
}
