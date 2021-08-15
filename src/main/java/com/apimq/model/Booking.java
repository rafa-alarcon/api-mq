package com.apimq.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Booking {
	@Id
	private long id;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@NotNull(message = "Check-in date cannot be empty")
	private String checkInDate;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@NotNull(message = "Check-out date cannot be empty")
	private String checkOutDate;
	
	@Min(value = 1, message = "Total Days should not be less than 1")
	private int totalDays;
	
	@Min(value = 1, message = "Guest number should not be less than 1")
    @Max(value = 30, message = "Guest number should not be greater than 30")
	private int guestNumber;
	
	@NotNull(message = "Owner cannot be empty")
	@Size(min = 1, max = 250, message 
    = "Owner must be between 1 and 250 characters")
	private String bookingOwner;
	
	@Email(message = "Email should be valid")
	@NotNull(message = "Email should be valid")
	@Size(min = 1, max = 250, message 
    = "Email must be between 1 and 250 characters")
	private String bookingOwnerEmail;
	
	@Min(value = 1, message = "Rooms should not be less than 1")
	private int rooms;
	
	@Min(value = 0, message = "Rooms should not be less than 0")
	private int childNumber;
}
