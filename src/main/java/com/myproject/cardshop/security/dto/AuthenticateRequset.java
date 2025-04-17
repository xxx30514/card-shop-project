package com.myproject.cardshop.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateRequset {
	
	@Email(message = "Email格式錯誤")
	@NotBlank(message = "Email欄位不可為空")
	private String email;
	
	@NotBlank(message = "密碼欄位不可為空")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).*$", message = "密碼需包含至少一個大寫字母與至少一個小寫字母")
	@Size(min = 8, max = 15, message = "密碼長度需介於8至15個字元")
	private String password;
	
}
