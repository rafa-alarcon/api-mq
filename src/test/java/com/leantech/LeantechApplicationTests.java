package com.leantech;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.apimq.controller.BookingController;

@SpringBootTest
class LeantechApplicationTests {
	
	@Autowired
	private BookingController bookingController;

	@Test
	void contextLoads() {
		assertThat(bookingController).isNotNull();
	}

}
