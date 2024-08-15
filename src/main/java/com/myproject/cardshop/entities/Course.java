package com.myproject.cardshop.entities;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String description;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(
		name = "courses_authors", 
		joinColumns = { @JoinColumn(name = "course_id")}, 
		inverseJoinColumns = {
			@JoinColumn(name = "author_id") 
		}
	)
	private List<Author> authors;
	
	@OneToMany(mappedBy = "course")//一對多 course為一  section為多 多用集合處理 mappedBy = "course" 指定Section的course屬性管理關聯
	@JsonManagedReference //雙向關聯時避免無窮迴圈 需要使用@JsonManagedReference註解表示主控方(一對多的一) 或用DTO處理雙向關係的遞迴問體
	private List<Section> sections;
}
