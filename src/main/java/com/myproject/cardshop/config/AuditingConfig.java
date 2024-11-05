package com.myproject.cardshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import com.myproject.cardshop.auditing.ApplicationAuditorAware;

@Configuration
public class AuditingConfig {
	
	/**
	 * 註冊 AuditorAware 的實現
	 * 審計功能 獲取當前使用者
	 * 這個Bean的名稱要與配置類相同 @EnableJpaAuditing(auditorAwareRef = "auditorAware")
	 */
	@Bean
	AuditorAware<String> auditorAware(){
		return new ApplicationAuditorAware();
	}
}
