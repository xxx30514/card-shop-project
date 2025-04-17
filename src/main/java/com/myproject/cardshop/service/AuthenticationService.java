package com.myproject.cardshop.service;

import com.myproject.cardshop.security.dto.AuthenticateRequset;
import com.myproject.cardshop.security.dto.AuthenticationResponse;
import com.myproject.cardshop.security.dto.RegisterRequset;

import jakarta.mail.MessagingException;

public interface AuthenticationService {

	AuthenticationResponse login(AuthenticateRequset request);
	
	void register(RegisterRequset request) throws MessagingException;

	void activateAccount(String token) throws MessagingException;
	
	void resendValidationEmail(String email) throws MessagingException;


}
