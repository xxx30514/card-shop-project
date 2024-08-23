package com.myproject.cardshop.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Author extends BaseEntity {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
	
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
	
	@ManyToMany(mappedBy = "authors",fetch = FetchType.EAGER)
	private List<Course> courses;
	
	
}
