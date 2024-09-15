package com.mq.demo.publisher;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mq.demo.model.OrderStatus;
import com.mq.demo.model.PurchaseOrder;

@RestController
@RequestMapping("/order")
public class PurchaseOrderPublisher {
	
	@Autowired
	private RabbitTemplate template;
	
	@Value("${mq.demo.exchange}")
	String exchange;
	
	@Value("${mq.demo.queue}")
	String queue_name;
	
	@Value("${mq.demo.routingkey}")
	String routing_key;

	@PostMapping("/{customer_name}")
	public String bookOrder(@RequestBody PurchaseOrder order,@PathVariable String customer_name) {
		order.setOrderID(UUID.randomUUID().toString());
		OrderStatus orderStatus = new OrderStatus(order,"ACCEPTED","order placed successfully by "+customer_name);
		template.convertAndSend(exchange,routing_key,orderStatus);
		return "success :)";
	}
}
