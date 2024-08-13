package com.myproject.cardshop.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@CreationTimestamp //首次插入資料時設置當前時間
	private LocalDateTime createTime;
	
	@UpdateTimestamp //更新時設置當前時間
	private LocalDateTime updateTime;
	
	private String createUser;
	
	private String updateUser;
}
