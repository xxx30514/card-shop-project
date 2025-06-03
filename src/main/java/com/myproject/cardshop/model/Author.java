package com.myproject.cardshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.myproject.cardshop.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)//有繼承關係時 子類別要使用
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
@Entity
@Table(name = "author_tbl")
@NamedQuery(name = "Author.findByNamedQuery", query = "select a from Author a where a.age >= :age")
//@NamedQuery(name = "Author.findByNamedQuery", query = "select a from Author a JOIN FETCH a.courses where a.age >= :age")
@NamedQuery(name = "Author.updateByNamedQuery", query = "update Author a set a.age = :age where a.id = :id")
@EntityListeners(AuditingEntityListener.class)
public class Author extends BaseEntity {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
	
	@Column(length = 50)
	private String firstName;
	
	private String lastName;
	
	@Column(unique = true,nullable = false, columnDefinition = "VARCHAR(255) COLLATE utf8_general_ci")
	private String email;
	
	private int age;
	
	@CreatedDate
	@Column(updatable = false,nullable = false,columnDefinition ="DATETIME(2)")
	private LocalDateTime createDateTime;
	
	@LastModifiedDate
	@Column(insertable = false)//沒有設定不可新增此欄位的話 在插入新資料時會自動設置為當前時間
	private LocalDateTime updateDateTime;
	
	@ManyToMany(mappedBy = "authors",fetch = FetchType.EAGER)
	private List<Course> courses;
	
	
}
