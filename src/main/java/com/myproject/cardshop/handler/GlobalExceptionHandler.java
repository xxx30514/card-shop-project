package com.myproject.cardshop.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<ExceptionResponse> handleLockedException(LockedException exception) {
		return buildExceptionResponse(ErrorCodes.ACCOUNT_LOCKED, exception);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<ExceptionResponse> handleDisabledException(DisabledException exception) {
		return buildExceptionResponse(ErrorCodes.ACCOUNT_DISABLED, exception);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException exception) {
		return buildExceptionResponse(ErrorCodes.BAD_CREDENTIALS, exception);
	}

	// 公共方法處理錯誤響應 減少重複程式碼
	private ResponseEntity<ExceptionResponse> buildExceptionResponse(ErrorCodes errorCode, Exception exception) {
		return ResponseEntity.status(errorCode.getHttpStatus())
				.body(ExceptionResponse.builder().errorCode(errorCode.getCode())
						.errorDescription(errorCode.getDescription()).error(exception.getMessage()).build());
	}
}
