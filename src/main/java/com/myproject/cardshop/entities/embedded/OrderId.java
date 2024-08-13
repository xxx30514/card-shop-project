package com.myproject.cardshop.entities.embedded;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable //標註此物件是一個嵌入式物件 可以嵌入到其他實體類中 做為複合主鍵時要實現序列化
public class OrderId implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userName;
	
	private LocalDateTime orderDate;
}
