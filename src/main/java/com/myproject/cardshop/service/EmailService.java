package com.myproject.cardshop.service;

import com.myproject.cardshop.email.EmailTemplateName;

import jakarta.mail.MessagingException;

public interface EmailService {
	
	void sendEmail(String to, String username, EmailTemplateName emailTemplate, String confirmationUrl,
			String activationCode, String subject) throws MessagingException;
}
