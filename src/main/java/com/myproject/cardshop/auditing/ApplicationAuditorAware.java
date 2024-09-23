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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()
				|| authentication instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}
		User user =(User) authentication.getPrincipal();
		return Optional.ofNullable(user.getEmail());
	}

}
