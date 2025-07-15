package com.myproject.cardshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.myproject.cardshop.common.response.ApiResponse;
import com.myproject.cardshop.model.User;
import com.myproject.cardshop.model.dto.UserDTO;
import com.myproject.cardshop.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserMapper userMapper;
	
	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser(Authentication authentication) {
	    User user = (User) authentication.getPrincipal();  // 從 token 中取得使用者
	    UserDTO dto = userMapper.toDto(user);
	    return ResponseEntity.ok(ApiResponse.success("查詢成功", dto));
	}
}
