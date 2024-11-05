package com.myproject.cardshop.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//1.預設值繼承策略 僅會建立一個父類表 子表的欄位(屬性)與父類的屬性會在同一個表中
//@DiscriminatorColumn(name = "resource_type")//1-1.預設名稱為dtype  搭配InheritanceType.SINGLE_TABLE 使用
//@Inheritance(strategy = InheritanceType.JOINED)//2.會建立父類表與子類表 父類表僅包含父類屬性欄位 子類表僅包含其特有屬性欄位與id(子類主鍵同時也是指向主表的外鍵)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)/*3.父類與子類皆有其對應的獨立資料表 且子類包含父類的所有屬性 
3-1.與@GeneratedValue(strategy = GenerationType.IDENTITY)使用時會與Hibernate的<union-subclass>繼承映射策略衝突
*/
public class Resource {
	
	@Id
	@GeneratedValue
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private int size;
	
	private String url;
	
	@OneToOne(mappedBy = "resource")//被擁有方設置mappedBy(無外鍵的一方)
	//@JoinColumn(name = "lecture_id")
	private Lecture lecture;
}
