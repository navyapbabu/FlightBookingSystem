package com.flightBooking.Controller;



import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightBooking.Dto.ResponseStructure;
import com.flightBooking.Exception.MissingInformationException;
import com.flightBooking.Exception.RecordAlreadyExistsException;
import com.flightBooking.Service.BookingService;
import com.flightBooking.entity.Booking;
import com.flightBooking.entity.Bookingstatus;
import com.flightBooking.entity.Passenger;
import com.flightBooking.entity.Payment;


@RestController
@RequestMapping("/booking")
public class BookingController
{
	@Autowired
	private BookingService bookingService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Booking>> createBooking(@RequestBody Booking booking) throws MissingInformationException, RecordAlreadyExistsException
	{
		return bookingService.createBooking(booking);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBookings()
	{
		return bookingService.getAllBookings();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(@PathVariable Integer id)
	{
		return bookingService.getBookingById(id);
	}
	
	@GetMapping("/flight/{airline}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByFlight(@PathVariable String airline)
	{
		return bookingService.getBookingByFlight(airline);
	}
	
	@GetMapping("/date/{date}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
	{
		return bookingService.getBookingByDate(date);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByStatus(@PathVariable Bookingstatus status)
	{
		return bookingService.getBookingByStatus(status);
	}
	
	@GetMapping("/passenger/{id}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengersInABooking(@PathVariable Integer id)
	{
		return bookingService.getAllPassengersInABooking(id);
	}
	
	@GetMapping("/payment/{id}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentOfBooking(@PathVariable Integer id)
	{
		return bookingService.getPaymentOfBooking(id);
	}
	
	@PutMapping("/{id}/{status}")
	public ResponseEntity<ResponseStructure<Booking>> updateBookingStatus(@PathVariable Integer id,@PathVariable Bookingstatus status)
	{
		return bookingService.updateBookingStatus(id, status);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Booking>> deleteBooking(@PathVariable Integer id)
	{
		return bookingService.deleteBooking(id);
	}
	
	@GetMapping("/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Booking>>> getBookingsByPaginationAndSorting(@PathVariable Integer pageNumber,@PathVariable Integer pageSize,@PathVariable String field )
	{
		return bookingService.getBookingsByPaginationAndSorting(pageNumber, pageSize, field);
	}
}
