package com.myproject.cardshop.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> roles;
	private String fullName;
}
