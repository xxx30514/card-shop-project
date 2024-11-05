package com.myproject.cardshop.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.myproject.cardshop.entities.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shop_user")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String firstName;

	private String lastName;

	private String email;

	private String userPassword;

	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * 提供使用者權限的集合 根據返回的結果決定使用者是否有權存取特定資源
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		return this.userPassword;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	/**
	 * 判斷使用者帳號是否過期
	 * 
	 * @return 如果帳號已過期 返回false 表示使用者不應該被授權
	 */
	@Override
	public boolean isAccountNonExpired() {
		// return true = 帳號不會過期
		return true;
	}

	/**
	 * 判斷使用者帳號是否鎖定 
	 * 通常是違反安全策略的臨時鎖定情況 例如輸入密碼錯誤次數過多 鎖定該帳號一段時間
	 * @return 如果帳號已鎖定 返回false 表示使用者不應該被授權
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 用來判斷使用者的認證資訊是否過期，例如Token是否過期
	 * 
	 * @return 如果認證資訊已過期 返回false 表示使用者不應該被授權
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 用來判斷使用者是否啟用
	 * 通常是管理員主動啟用或禁用帳號 例如禁止違規帳號
	 * @return 如果使用者已被禁用 返回false 表示使用者不應該被授權
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}
