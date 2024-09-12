package com.myproject.cardshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    //
    registry.addMapping("/**")
        // 是否發送cookie
        .allowCredentials(true)
        // 放行原始域名
        .allowedOriginPatterns("*")
        // 放行的請求方式
        .allowedMethods("*")
        // 放行那些HTTP header
        .allowedHeaders("*").exposedHeaders("*");
  }
}
