package com.leantech.service;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.apimq.model.Booking;
import com.apimq.service.RabbitMQSenderService;

@ExtendWith(MockitoExtension.class)
public class RabbitMQSenderServiceTest {

	@Mock
	private AmqpTemplate amqpTemplate;
	
	@InjectMocks
	private RabbitMQSenderService senderService;
	
	private Booking booking;
	String exchange = "exchange";
	String routingKey = "routingKey";
	@BeforeEach
	void setUp() {
		booking = new Booking();
		booking.setId(1);
		booking.setBookingOwner("Test");
		booking.setBookingOwnerEmail("test@leantech.com");
		booking.setCheckInDate("2021-08-12");
		booking.setCheckOutDate("2021-08-12");
		booking.setChildNumber(1);
		booking.setGuestNumber(2);
		booking.setRooms(1);
		booking.setTotalDays(1);
		ReflectionTestUtils.setField(senderService, "exchange", exchange);
		ReflectionTestUtils.setField(senderService, "routingkey", routingKey);
	}
	@Test
	void callSendTest() {
		senderService.send(booking);
		verify(amqpTemplate, atLeastOnce()).convertAndSend(exchange, routingKey, booking);
	}
}
