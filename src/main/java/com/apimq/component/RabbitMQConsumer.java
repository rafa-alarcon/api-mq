package com.apimq.component;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.apimq.model.Booking;
import com.apimq.service.BookingService;
import com.apimq.util.BookingValidator;

@Component
public class RabbitMQConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class); 
	
	private BookingService bookingservice;
	
	private BookingValidator validator = new BookingValidator();
	
	public RabbitMQConsumer(@Autowired BookingService bookingservice) {
		super();
		this.bookingservice = bookingservice;
	}



	@RabbitListener(queues = "${rabbitmq.queue}")
	public void receiveMessage(@Valid @Payload Booking booking) throws IllegalArgumentException {
		validator.validate(booking);
		LOGGER.debug("Record Passed Validation : {}",booking);
		bookingservice.saveBooking(booking);
	}
}
