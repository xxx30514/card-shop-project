package com.myproject.cardshop.model;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Principal {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String firstName;

	private String lastName;
	
	@Column(unique = true)
	private String email;

	private String userPassword;
	
	private boolean accountLocked;
	
	private boolean accountEnabled;
	
//	@Enumerated(EnumType.STRING)
//	private Role role;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles;
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime updatedDate;
	
//	@CreatedBy
//	@Column(updatable = false)
//	private String createdUser;
//	
//	@LastModifiedBy
//	@Column(insertable = false)
//	private String updatedUser;
	
	@Override
	public String getName() {
		return this.email;
	}

	/**
	 * 提供使用者權限的集合 根據返回的結果決定使用者是否有權存取特定資源
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//return List.of(new SimpleGrantedAuthority(role.name()));
		return this.roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
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
		return !this.accountLocked;
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
	 * 通常是管理員主動啟用或禁用帳號 例如禁止違規帳號 或是尚未經過認證
	 * @return 如果使用者已被禁用 返回false 表示使用者不應該被授權
	 */
	@Override
	public boolean isEnabled() {
		return this.accountEnabled;
	}
	
	public String getFullName() {
		return this.lastName + " " + this.firstName;
	}
}
