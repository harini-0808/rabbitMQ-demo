package com.mq.demo.consumer;

import java.util.logging.Logger;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.mq.demo.model.OrderStatus;

public class PurchaseOrderConsumer {
//	@Autowired
//	private RabbitTemplate template;
	
	
	Logger logger = Logger.getLogger("PurchaseOrderConsumer");

	@Value("${mq.demo.exchange}")
	String exchange;
	
	@Value("${mq.demo.queue}")
	String queue_name;
	
	@Value("${mq.demo.routingkey}")
	String routing_key;
	
	@RabbitListener(queues = "mq_queue")
	public void consumeMessageFromQueue(OrderStatus orderStatus) {
		logger.info("Message received from Queue : "+orderStatus);
		System.out.println(orderStatus);
	}
	
	

}
