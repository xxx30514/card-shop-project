package com.myproject.cardshop.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Section {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private int sectionOrder;
	
	@ManyToOne
	@JoinColumn(name = "course_id")//多對一 在多的一方建立外來鍵 
	@JsonBackReference //雙向關聯時避免無窮迴圈 需要使用@JsonBackReference註解表示被控方(一對多的多) 或用DTO處理雙向關係的遞迴問體
	private Course course;
	
	@OneToMany(mappedBy = "section")
	//一對多 多的一方使用集合  mappedBy = "section"  告訴JPA這個關係用Lecture實體中的 section屬性管理 lecture為多  section為一
	private List<Lecture> lectures;
	
	
	
}
