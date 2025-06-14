package com.myproject.cardshop.common.exception;

import java.util.HashSet;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.mail.MessagingException;

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

	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<ExceptionResponse> handleMessagingException(MessagingException exception) {
		return buildExceptionResponse(ErrorCodes.EMAIL_SENDING_FAILED, exception);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		Set<String> errors = new HashSet<>();
		exception.getBindingResult().getAllErrors().forEach(error -> {
			String errorMessage = error.getDefaultMessage();
			errors.add(errorMessage);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ExceptionResponse.builder().validationErrors(errors).build());
	}
//	@ExceptionHandler(MethodArgumentNotValidException.class) 還未測試
//	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//	    Map<String, String> errors = new HashMap<>();
//	    exception.getBindingResult().getFieldErrors().forEach(error -> {
//	        errors.put(error.getField(), error.getDefaultMessage());
//	    });
//	    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//	        .body(ExceptionResponse.builder()
//	            .errorCode(400)
//	            .errorDescription("參數驗證錯誤")
//	            .errors(errors)
//	            .build());
//	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
		exception.printStackTrace();
		return buildExceptionResponse(ErrorCodes.INTERNAL_ERROR, exception);
	}

	@ExceptionHandler({ ExpiredJwtException.class, MalformedJwtException.class, SignatureException.class })
	public ResponseEntity<ExceptionResponse> handleJwtException(Exception exception) {
		return buildExceptionResponse(ErrorCodes.JWT_INVALID, exception);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
		return buildExceptionResponse(ErrorCodes.USER_NOT_FOUND, exception);
	}
	
	// 公共方法處理多數錯誤響應 減少重複程式碼
	private ResponseEntity<ExceptionResponse> buildExceptionResponse(ErrorCodes errorCode, Exception exception) {
		return ResponseEntity.status(errorCode.getHttpStatus())
				.body(ExceptionResponse.builder().errorCode(errorCode.getCode())
						.errorDescription(errorCode.getErrorDescription(exception)).error(exception.getMessage()).build());
	}
}
