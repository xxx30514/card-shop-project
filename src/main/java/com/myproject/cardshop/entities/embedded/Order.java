package com.myproject.cardshop.entities.embedded;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_tbl")//order為保留字不能做為資料表名稱
public class Order {
	
	@EmbeddedId//此註解僅能存在一個且不能與@Id註解一起使用
	private OrderId id;
	
	private String orderInfo;

	private String anotherFiled;
}
