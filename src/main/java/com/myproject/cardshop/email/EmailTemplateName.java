package com.myproject.cardshop.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
	
	ACTIVATE_ACCOUNT("active_account");
	
	private final String name;

	private EmailTemplateName(String name) {
		this.name = name;
	}
	
}
