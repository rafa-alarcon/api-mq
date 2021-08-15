package com.apimq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apimq.model.Booking;
import com.apimq.service.BookingService;
import com.apimq.service.RabbitMQSenderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class BookingController {
	@Autowired
	BookingService bookingService;
	
	@Autowired
	RabbitMQSenderService rabbitMQSender;
	
	@PostMapping(value ="/registrar-reserva", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Adds the current request to RabbitMQ queue to be processed later by the consumer")
	public void saveBooking(@ApiParam(value="Object with all the needed information to book the room", required=true) @RequestBody Booking booking) {
		 rabbitMQSender.send(booking);
	}
	
	@GetMapping(value="/consultar-reserva", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Search a previous booking")
	public Booking getBooing(@RequestBody Booking booking) {
		Booking result = bookingService.findBookingById(booking.getId());
		
		return result;
	}
}
