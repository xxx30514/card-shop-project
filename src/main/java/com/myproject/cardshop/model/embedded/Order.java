package com.myproject.cardshop.model.embedded;

import jakarta.persistence.Embedded;
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
	
	@EmbeddedId//此註解僅能存在一個且不能與@Id註解一起使用 實現複合主鍵
	private OrderId id;
	
	@Embedded //將可嵌入的實體(@Embeddable)嵌入到此(Order)實體中 Address的屬性會映射到Order中 減少重複程式碼
	private Address address;
	
	private String orderInfo;

	private String anotherFiled;
}
