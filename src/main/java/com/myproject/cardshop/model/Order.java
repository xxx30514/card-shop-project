package com.myproject.cardshop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@TableName("t_order")
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	//@TableId
	private Integer orderId;
	private Long orderNum;
	private Integer userId;
	private Integer productId;
	private Integer productNum;
	private BigDecimal productPrice;
	private LocalDateTime orderTime;
	private Integer addressId;

}
