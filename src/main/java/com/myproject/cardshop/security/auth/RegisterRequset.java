package com.myproject.cardshop.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequset {
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
}
