package com.apimq.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apimq.model.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

}
