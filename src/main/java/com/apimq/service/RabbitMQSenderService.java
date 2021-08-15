package com.apimq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apimq.model.Booking;

@Service
public class RabbitMQSenderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSenderService.class);
	private AmqpTemplate amqpTemplate;
	
	
	
	public RabbitMQSenderService(@Autowired AmqpTemplate amqpTemplate) {
		super();
		this.amqpTemplate = amqpTemplate;
	}


	@Value("${rabbitmq.exchange}")
	private String exchange;
	
	@Value("${rabbitmq.routingkey}")
	private String routingkey;	
	
	
	public void send(Booking booking) {
		amqpTemplate.convertAndSend(exchange, routingkey, booking);
		LOGGER.debug("Send msg = {}", booking);
	    
	}
}