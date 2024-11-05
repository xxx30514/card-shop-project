package com.myproject.cardshop.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
//@PrimaryKeyJoinColumn(name = "video_id") //與strategy = InheritanceType.JOINED 使用 指定子表的id(外鍵)名稱
//@DiscriminatorValue("V") //與strategy = InheritanceType.SINGLE_TABLE 使用

public class Video extends Resource {
	
	private int length;
}
