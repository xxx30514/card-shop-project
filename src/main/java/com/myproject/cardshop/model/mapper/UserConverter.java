package com.myproject.cardshop.model.dto;

import java.util.List;

import com.myproject.cardshop.model.User;

public class UserConverter {

	public static UserDTO toDto(User user) {
		List<String> roleNames = user.getRoles().stream().map(role -> role.getName()).toList();
		return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), roleNames,
				user.getFullName());
	}
}
