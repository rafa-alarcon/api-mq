package com.leantech.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.apimq.model.Booking;
import com.apimq.service.BookingService;
import com.apimq.service.RabbitMQSenderService;
import com.google.gson.Gson;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RabbitMQSenderService senderService;
	
	@MockBean
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
	void testRegistrarReservaEndpoint() throws Exception {
		Gson gson = new Gson();
		mockMvc.perform(post("/registrar-reserva")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(booking, Booking.class))
				)
		.andExpect(status().isNoContent());
	}
	
	@Test
	void testConsultarReservaExist() throws Exception {
		Gson gson = new Gson();
		when(bookingService.findBookingById(1L)).thenReturn(booking);
		mockMvc.perform(get("/consultar-reserva")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(booking, Booking.class))
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)));
	}
	
	@Test
	void testConsultarReservaDoesNotExist() throws Exception {
		Gson gson = new Gson();
		mockMvc.perform(get("/consultar-reserva")
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(new Booking(), Booking.class))
				)
		.andExpect(status().isNoContent());
	}
}
