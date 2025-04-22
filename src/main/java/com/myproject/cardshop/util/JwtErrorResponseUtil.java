package com.myproject.cardshop.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.cardshop.exception.ErrorCodes;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;

public class JwtErrorResponseUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void writeJwtErrorResponse(HttpServletResponse response, Exception exception) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");

		String errorMessage;
		if (exception instanceof ExpiredJwtException) {
			errorMessage = "JWT已過期";
		} else if (exception instanceof MalformedJwtException) {
			errorMessage = "JWT格式錯誤";
		} else if (exception instanceof SignatureException) {
			errorMessage = "JWT簽名錯誤";
		} else {
			errorMessage = "JWT錯誤，請重新登入";
		}

		Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("errorCode", ErrorCodes.JWT_INVALID.getCode());
		errorBody.put("errorDescription", ErrorCodes.JWT_INVALID.getDescription());
		errorBody.put("error", errorMessage);

		objectMapper.writeValue(response.getWriter(), errorBody);
	}
}
