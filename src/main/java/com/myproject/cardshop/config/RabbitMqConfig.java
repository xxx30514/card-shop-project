package com.myproject.cardshop.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	public static final String EXCHANGE_NAME = "order.exchange";

	// 清除購物車隊列和Routing Key
	public static final String CLEAR_QUEUE = "clear.queue";

	public static final String CLEAR_ROUTING_KEY = "clear.cart";

	// 更新庫存隊列和Routing Key
	public static final String UPDATE_QUEUE = "update.queue";

	public static final String UPDATE_ROUTING_KEY = "update.number";

	@Bean
	public DirectExchange orderExchange() {
		return new DirectExchange(EXCHANGE_NAME, true, false);
	}

	@Bean
	public Queue clearQueue() {
		return new Queue(CLEAR_QUEUE, true);
	}

	@Bean
	public Queue updateQueue() {
		return new Queue(UPDATE_QUEUE, true);
	}

	// 綁定清除購物車隊列到交換機，指定 Routing Key
	@Bean
	public Binding bindingClearQueue(Queue clearQueue, DirectExchange orderExchange) {
		return BindingBuilder.bind(clearQueue).to(orderExchange).with(CLEAR_ROUTING_KEY);
	}

	// 綁定更新庫存隊列到交換機，指定 Routing Key
	@Bean
	public Binding bindingUpdateQueue(Queue updateQueue, DirectExchange orderExchange) {
		return BindingBuilder.bind(updateQueue).to(orderExchange).with(UPDATE_ROUTING_KEY);
	}

	/**
	 * RabbitMQ序列化方式 Json 格式
	 * 
	 * @return
	 */
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
