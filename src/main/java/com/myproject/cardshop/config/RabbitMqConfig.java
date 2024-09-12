package com.myproject.cardshop.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
	/**
	 * RabbitMQ序列化方式 Json 格式
	 * @return
	 */
	@Bean
	MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

}
