package com.myproject.cardshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.cardshop.model.User;
import com.myproject.cardshop.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/demo-controller")
@RequiredArgsConstructor
public class DemoController {

	private final UserRepository userRepository;

	@GetMapping
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello World");
	}

	@GetMapping("/{email}")
	public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable("email") String email) {
		return ResponseEntity.ok(userRepository.findByEmail(email));
	}
}
