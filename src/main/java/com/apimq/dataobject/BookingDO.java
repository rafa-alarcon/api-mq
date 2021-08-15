package com.apimq.dataobject;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class BookingDO {
	@Id
	long id;
	String bookingOwner;
	LocalDate checkInDate;
	LocalDate checkOutDate;
	int totalDays;
	int guestNumber;
	int rooms;
	int childNumber;
}
