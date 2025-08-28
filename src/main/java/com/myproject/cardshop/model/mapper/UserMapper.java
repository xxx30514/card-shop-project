package com.myproject.cardshop.model.mapper;

import java.util.List;
import org.springframework.stereotype.Service;
import com.myproject.cardshop.model.User;
import com.myproject.cardshop.model.dto.UserDTO;

@Service
public class UserMapper {

//	public UserDTO toDto(User user) {
//		List<String> roleNames = user.getRoles().stream().map(role -> role.getName()).toList();
//		return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), roleNames,
//				user.getFullName());
//	}
	public UserDTO toDto(User user) {
		List<String> roleNames = user.getRoles().stream().map(role -> role.getName()).toList();
		return UserDTO.builder().id(user.getId()).firstName(user.getFirstName()).lastName(user.getLastName())
				.email(user.getEmail()).roles(roleNames).fullName(user.getFullName()).build();
	}
}
