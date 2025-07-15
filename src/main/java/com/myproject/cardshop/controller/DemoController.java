package com.myproject.cardshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myproject.cardshop.common.response.ApiResponse;
import com.myproject.cardshop.model.User;
import com.myproject.cardshop.model.dto.UserDTO;
import com.myproject.cardshop.model.mapper.UserMapper;
import com.myproject.cardshop.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/demo-controller")
@RequiredArgsConstructor
public class DemoController {

	private final UserRepository userRepository;
	
	private final UserMapper userMapper;

	@GetMapping
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello World");
	}

	@GetMapping("/{email}")
	public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@PathVariable("email") String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		User user = optionalUser.orElse(null);
		UserDTO dto = userMapper.toDto(user);
		return ResponseEntity.ok(ApiResponse.success("查詢成功",dto));
	}
	 @GetMapping("/test/expired")
	    public String throwExpiredJwtException() {
	        throw new ExpiredJwtException(null, null, "模擬：JWT 已過期");
	    }

	    @GetMapping("/test/malformed")
	    public String throwMalformedJwtException() {
	        throw new MalformedJwtException("模擬：JWT 格式錯誤");
	    }

	    @GetMapping("/test/signature")
	    public String throwSignatureException() {
	        throw new SignatureException("模擬：JWT 簽名無效");
	    }
	    @GetMapping("/test/messaging")
	    public String throwMessagingException(Exception exception) throws MessagingException {
	        throw new  MessagingException(exception.getMessage());
	    }
	  
}
