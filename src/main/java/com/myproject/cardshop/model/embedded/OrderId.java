package com.myproject.cardshop.model.embedded;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable //標註此類是一個嵌入式實體 可以嵌入到其他實體類中 建議實現序列化
public class OrderId implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userName;
	
	private LocalDateTime orderDate;
}
