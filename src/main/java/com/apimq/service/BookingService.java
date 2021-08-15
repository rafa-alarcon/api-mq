package com.apimq.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apimq.data.BookingRepository;
import com.apimq.model.Booking;


@Service
public class BookingService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class); 
	
	private BookingRepository bookingRepository;
	private EmailService emailService;
	
	

	public BookingService(@Autowired BookingRepository bookingRepository, @Autowired EmailService emailService) {
		super();
		this.bookingRepository = bookingRepository;
		this.emailService = emailService;
	}

	public Booking findBookingById(Long id) {
		LOGGER.debug("Searching id {}", id);
		Optional<Booking> result = bookingRepository.findById(id);
		return result.isPresent()? result.get(): null;
	}
	
	public Booking saveBooking(Booking booking) {
		Booking result = null;
		try {
			result = bookingRepository.save(booking);
		}
		catch(IllegalArgumentException e) {
			emailService.sendFailureMessage(booking.getBookingOwnerEmail());
			throw e;
		}
		emailService.sendSuccessMessage(booking.getBookingOwnerEmail());;
		return result;
	}
}
