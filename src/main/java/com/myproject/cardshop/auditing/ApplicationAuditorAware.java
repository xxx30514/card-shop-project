package com.myproject.cardshop.auditing;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.myproject.cardshop.entities.User;

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
			return Optional.empty();
		}
		User user = (User) authentication.getPrincipal();
		return Optional.ofNullable(user.getUsername());
	}

}
