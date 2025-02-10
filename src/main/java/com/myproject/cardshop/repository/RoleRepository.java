package com.myproject.cardshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.cardshop.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(String role);
}
