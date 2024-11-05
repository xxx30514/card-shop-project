package com.myproject.cardshop.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder//有繼承關係時使用 父子類別都要使用  @Builder 無繼承關係時使用
@MappedSuperclass //定義共享屬性與方法提供子類繼承 不會建立對應表格
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createTime;
	
	@LastModifiedDate
	private LocalDateTime updateTime;
	
	@CreatedBy
	@Column(updatable = false)
	private String createUser;
	
	@LastModifiedBy
	@Column(insertable = false)
	private String updateUser;
}
