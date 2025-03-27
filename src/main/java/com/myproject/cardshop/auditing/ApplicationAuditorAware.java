package com.myproject.cardshop.auditing;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 透過spring security 獲取當前使用者AuditorAware<T> 獲取ID、使用者名稱、email等<String>or<Integer>
 */
public class ApplicationAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// 取得當前認證資訊
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 檢查認證狀態
		// authentication == null 無認證資訊
		// !authentication.isAuthenticated() 未通過認證
		// authentication instanceof AnonymousAuthenticationToken 表示匿名使用者
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return Optional.of("anonymous"); // 返回預設值
		}
		// 確保principal是UserDetails避免ClassCastException轉換不兼容
		Object principal = authentication.getPrincipal();
		if (principal instanceof UserDetails) {
			return Optional.of(((UserDetails) principal).getUsername());
		}
		// 處理其他類型的principal例如JWT中解析的username
		if (principal instanceof String) {
			return Optional.of((String) principal);
		}
		// 回傳預設值 避免null錯誤
		return Optional.of("anonymous");
	}

}
