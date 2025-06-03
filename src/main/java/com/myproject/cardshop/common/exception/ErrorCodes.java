package com.myproject.cardshop.common.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;

public enum ErrorCodes {

	NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "NO code"),

	INCORRECT_CURRENT_PASSWORD(300, HttpStatus.BAD_REQUEST, "密碼錯誤"),

	NEW_PASSWORD_DOES_NOT_MATCH(301, HttpStatus.BAD_REQUEST, "密碼錯誤"),

	ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "您的帳號已被鎖定，請聯絡管理員"),

	ACCOUNT_DISABLED(303, HttpStatus.FORBIDDEN, "您的帳號尚未啟用，請進行email驗證"),

	BAD_CREDENTIALS(304, HttpStatus.UNAUTHORIZED, "帳號或密碼錯誤，請重新輸入"),

	EMAIL_SENDING_FAILED(305, HttpStatus.INTERNAL_SERVER_ERROR, "信件發送失敗，請稍後再試"),

	INTERNAL_ERROR(306, HttpStatus.INTERNAL_SERVER_ERROR, "發生未知錯誤，請稍後再試"),

	JWT_INVALID(307, HttpStatus.UNAUTHORIZED, "JWT錯誤，請重新登入", createJwtExceptionMessages()),
	
	USER_NOT_FOUND(308, HttpStatus.NOT_FOUND, "查無此帳號");

	@Getter
	private final int code;

	@Getter
	private final String description;

	@Getter
	private final HttpStatus httpStatus;

	private final Map<Class<? extends Exception>, String> exceptionMessages;

	private static Map<Class<? extends Exception>, String> createJwtExceptionMessages() {
		Map<Class<? extends Exception>, String> map = new HashMap<>();
		map.put(ExpiredJwtException.class, "JWT已過期");
		map.put(MalformedJwtException.class, "JWT格式錯誤");
		map.put(SignatureException.class, "JWT簽名錯誤");
		return map;
	}

	private ErrorCodes(int code, HttpStatus httpStatus, String description) {
		this.code = code;
		this.description = description;
		this.httpStatus = httpStatus;
		this.exceptionMessages = Collections.emptyMap();
	}

	private ErrorCodes(int code, HttpStatus httpStatus, String description,
			Map<Class<? extends Exception>, String> exceptionMessages) {
		this.code = code;
		this.description = description;
		this.httpStatus = httpStatus;
		this.exceptionMessages = exceptionMessages;
	}

	public String getErrorDescription(Exception exception) {
		// 根據例外類別取得自訂錯誤訊息，若無則回傳預設敘述
		return exceptionMessages.getOrDefault(exception.getClass(), this.description);
	}
}
