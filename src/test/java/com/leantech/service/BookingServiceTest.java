package com.leantech.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apimq.data.BookingRepository;
import com.apimq.model.Booking;
import com.apimq.service.BookingService;
import com.apimq.service.EmailService;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
	@Mock
	private BookingRepository bookingRepository;
	@Mock
	private EmailService emailService;
	@InjectMocks
	private BookingService bookingService;
	private Booking booking;
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
	}
	@Test
	void savedBookingTest() {
		when(bookingRepository.save(ArgumentMatchers.any(Booking.class))).thenReturn(booking);
		Booking savedBooking = bookingService.saveBooking(booking);
		verify(bookingRepository, atLeastOnce()).save(booking);
		assertThat(savedBooking).isNotNull();
		assertEquals(savedBooking, booking);
	}
	
	@Test
	void findBookingExist() {
		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
		Booking result = bookingService.findBookingById(1L);
		verify(bookingRepository, atLeastOnce()).findById(1L);
		assertThat(result).isNotNull();
		assertEquals(1L,result.getId());
	}
	
	@Test
	void findBookingDoesNotExist() {
		Booking result = bookingService.findBookingById(2L);
		verify(bookingRepository, atLeastOnce()).findById(2L);
		assertThat(result).isNull();
	}
}
