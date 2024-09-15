package com.mq.demo.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.ConnectionFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
	
	@Value("${mq.demo.exchange}")
	String exchange;
	
	@Value("${mq.demo.queue}")
	String queue_name;
	
	@Value("${mq.demo.routingkey}")
	String routing_key;

	@Bean
	public Queue queue() {
		return new Queue(queue_name);
	}
	
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(exchange);
	}
	
	@Bean
	public Binding biding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routing_key);
	}
	
	@Bean 
	public MessageConverter converter() {
		return new Jackson2JsonMessageConverter();
	}
	

	@Bean
	public AmqpTemplate template(ConnectionFactory connectionFactory) {
		
		final RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(converter());
		return template;
	}
	
	
	

}
