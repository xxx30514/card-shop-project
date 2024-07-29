package com.myproject.cardshop.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "author_tbl")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 50)
	private String firstName;
	
	private String lastName;
	
	@Column(unique = true,nullable = false)
	private String email;
	
	private int age;

	@Column(updatable = false,nullable = false,columnDefinition ="DATETIME(2)")
	private LocalDateTime createDateTime;
	
	@Column(insertable = false)
	private LocalDateTime updateDateTime;
	
	
}
