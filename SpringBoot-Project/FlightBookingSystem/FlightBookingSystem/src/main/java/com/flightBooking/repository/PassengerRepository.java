package com.flightBooking.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flightBooking.entity.Passenger;


public interface PassengerRepository extends JpaRepository<Passenger, Integer>
{
	public Optional<Passenger> findByContactNo(Long contactNo);
	
	@Query("SELECT p FROM Passenger p WHERE p.booking.flight.id = :id")
	List<Passenger> getPassengersByFlight(Integer id);

}

