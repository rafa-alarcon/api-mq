package com.apimq.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apimq.model.Booking;

public class BookingValidator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookingValidator.class);
	private static final String INVALID_OWNER = "Invalid booking owner";
	private static final String INVALID_CHECK_IN = "Invalid check-in date";
	private static final String INVALID_CHECK_OUT = "Invalid check-out date";
	private static final String INVALID_DATE_FORMAT = "Invalid date format";
	private static final String INVALID_GUEST_NUMBER = "Invalid Guest Number";
	
	public void validate(Booking booking) {
		isValidGuestNumber(booking.getGuestNumber());
		// doing parse of the dates will validate that we have valid ISO date
		LocalDate checkInDate = getLocalDate(booking.getCheckInDate());
		LocalDate checkOutDate = getLocalDate(booking.getCheckOutDate());
		LocalDate today = LocalDate.now();
		isValidCheckOutDate(checkOutDate, checkInDate);
		isValidCheckInDate(today, checkInDate);
		isValidOwner(booking.getBookingOwner());
		
	}
	
	private void isValidOwner(String bookingOwner) {
		if(bookingOwner == null || bookingOwner.isEmpty() || bookingOwner.isBlank() ) {
			LOGGER.error(INVALID_OWNER);
			throw new IllegalArgumentException(INVALID_OWNER);
		}
	}
	
	private void isValidCheckInDate(LocalDate today, LocalDate checkInDate) {
		if(checkInDate.isBefore(today) || checkInDate.isEqual(today)) {
			LOGGER.error(INVALID_CHECK_IN);
			throw new IllegalArgumentException(INVALID_CHECK_IN);
		}
	}
	
	private void isValidCheckOutDate(LocalDate checkOutDate, LocalDate checkInDate) {
		if(checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
			LOGGER.error(INVALID_CHECK_OUT);
			throw new IllegalArgumentException(INVALID_CHECK_OUT);
		}
	}
	
	private LocalDate getLocalDate(String date) {
		try {
		return LocalDate.parse(date);
		}
		catch (DateTimeParseException e) {
			LOGGER.error(INVALID_DATE_FORMAT,e);
			throw new IllegalArgumentException(INVALID_DATE_FORMAT,e);
		}
	}
	
	private void isValidGuestNumber(int guestNumber) {
		if(guestNumber<= 0) {
			LOGGER.error(INVALID_GUEST_NUMBER);
			throw new IllegalArgumentException(INVALID_GUEST_NUMBER);
		}
	}
}
