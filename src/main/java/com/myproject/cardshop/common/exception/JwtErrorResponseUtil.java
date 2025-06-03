package com.myproject.cardshop.common.exception;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 給JwtAuthenticationFilter使用的例外返回工具類 Filter在請求進入controller前就會處理 全局例外處理器無法處理
 * 將返回內容封裝成與全局例外處理器相同 統一風格
 */
public class JwtErrorResponseUtil {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void writeJwtErrorResponse(HttpServletResponse response, Exception exception) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		ExceptionResponse responseBody = ExceptionResponse.builder().errorCode(ErrorCodes.JWT_INVALID.getCode())
				.errorDescription(ErrorCodes.JWT_INVALID.getErrorDescription(exception)).error(exception.getMessage())
				.build();
		objectMapper.writeValue(response.getWriter(), responseBody);
	}
}
