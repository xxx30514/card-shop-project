package com.myproject.cardshop;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.myproject.cardshop.entities.Author;
import com.myproject.cardshop.repositories.AuthorRepository;

import lombok.experimental.var;

@SpringBootApplication
public class CardshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardshopApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(AuthorRepository repository) {
		return args->{
			Author author =Author.builder().firstName("Jason").lastName("Yeh").age(31).email("test@gmail.com").createDateTime(LocalDateTime.now()).build();
			repository.save(author);
		};
	}
}
