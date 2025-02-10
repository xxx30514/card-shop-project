package com.myproject.cardshop.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import com.myproject.cardshop.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
/**
 * 1.允許特定請求無需驗證
 * 2.確保其他API需要驗證
 * 3.使用authenticationProvider來處理身份驗證
 * 4.設置jwtAuthenticationFilter來驗證JWT
 * 5.使用無狀態(stateless)方式處理請求(不依賴Session)
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)//使用 @Secured註解來限制方法的訪問權限 也可使用@PreAuthorize註解 
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private final AuthenticationProvider authenticationProvider;

	/**
	 * 舊寫法 httpSecurity .csrf() .disable() .authorizeHttpRequests()
	 * .requestMatchers("") .permitAll() .anyRequest() .authenticated() .and()
	 * .sessionManagement() .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	 * .and() .authenticationProvider(authenticationProvider)
	 * .addFilterBefore(jwtAuthenticationFilter,
	 * UsernamePasswordAuthenticationFilter.class);
	 * 
	 * @param httpSecurity
	 * @return
	 * @throws Exception 可另外加異常處理.exceptionHandling(handling -> handling
	 *                   .authenticationEntryPoint((request, response,
	 *                   authException) -> { if (authException instanceof
	 *                   UsernameNotFoundException) {
	 *                   response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	 *                   response.getWriter().write("查無此帳號"); } else {
	 *                   response.sendError(HttpServletResponse.SC_FORBIDDEN,
	 *                   "Forbidden"); } }))
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())).csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/authentications/**").permitAll()// 定義不須過濾的請求路徑
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// 設定為無狀態
				.authenticationProvider(authenticationProvider) // 使用自定義的 AuthenticationProvider 處理身分驗證
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);// 過濾器的順序設定 
		return httpSecurity.build();
	}
}
