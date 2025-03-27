package com.myproject.cardshop.handler;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public enum ErrorCodes {
	
	NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "NO code"),
	
	INCORRECT_CURRENT_PASSWORD(300,HttpStatus.BAD_REQUEST,"密碼錯誤"),
	
	NEW_PASSWORD_DOES_NOT_MATCT(301,HttpStatus.BAD_REQUEST,"密碼錯誤"),
	
	ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "您的帳號已被鎖定，請聯絡管理員"),
	
	ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "您的帳號尚未啟用，請進行email驗證"),
	
	BAD_CREDENTIALS(304, HttpStatus.UNAUTHORIZED, "帳號或密碼錯誤，請重新輸入");

	@Getter
	private final int code;

	@Getter
	private final String description;

	@Getter
	private final HttpStatus httpStatus;

	private ErrorCodes(int code, HttpStatus httpStatus, String description) {
		this.code = code;
		this.description = description;
		this.httpStatus = httpStatus;
	}

}
