package com.flightBooking.repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flightBooking.entity.Booking;
import com.flightBooking.entity.Bookingstatus;
import com.flightBooking.entity.Passenger;
import com.flightBooking.entity.Payment;




public interface BookingRepository extends JpaRepository<Booking, Integer>
{
	@Query("SELECT b FROM Booking b WHERE b.flight.airline = :airline")
	public List<Booking> getBookingByFlight(String airline);
	
	@Query("SELECT b FROM Booking b WHERE FUNCTION('DATE', b.bookingDate) = :date")
	public List<Booking> getBookingByDate(LocalDate date);
	
	public List<Booking> findByStatus(Bookingstatus status);
	
	@Query("SELECT b.passenger FROM Booking b WHERE b.id = :id")
	public List<Passenger> getAllPassengerInABooking(Integer id);
	
	@Query("SELECT b.payment FROM Booking b WHERE b.id = :id")
	public Optional<Payment> getPaymentOfBooking(Integer id);
	
	
}

