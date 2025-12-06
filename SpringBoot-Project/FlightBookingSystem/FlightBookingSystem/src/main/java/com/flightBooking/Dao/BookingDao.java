package com.flightBooking.Dao;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightBooking.Exception.*;
import com.flightBooking.entity.*;
import com.flightBooking.repository.BookingRepository;


@Repository
public class BookingDao
{
	@Autowired
	private BookingRepository bookingRepository;
	
	public Booking createBooking(Booking booking)
	{
		
		return bookingRepository.save(booking);
	}
	
	public List<Booking> getAllBookings()
	{
		return bookingRepository.findAll();
	}
	
	public Booking getBookingById(Integer id)
	{
		Optional<Booking> opt=bookingRepository.findById(id);
		if(opt.isPresent())
		{
			return opt.get();
		}
		else
		{
			throw new IdNotFoundException("id not present in db");
		}
	}
	
	public List<Booking> getBookingByFlight(String airline)
	{
		List<Booking> bookings=bookingRepository.getBookingByFlight(airline);
		if(!bookings.isEmpty())
		{
			return bookings;
		}
		else
		{
			throw new NoRecordAvailableException("No booking available for this airline");
		}
	}
	
	public List<Booking> getBookingByDate(LocalDate date)
	{
		List<Booking> bookings=bookingRepository.getBookingByDate(date);
		if(!bookings.isEmpty())
		{
			return bookings;
		}
		else
		{
			throw new NoRecordAvailableException("No booking available on this date");
		}
	}
	
	public List<Booking> getBookingByStatus(Bookingstatus status)
	{
		List<Booking> bookings=bookingRepository.findByStatus(status);
		if(!bookings.isEmpty())
		{
			return bookings;
		}
		else
		{
			throw new NoRecordAvailableException("No booking available with status : "+status);
		}
	}
	
	public List<Passenger> getAllPassengersInABooking(Integer id)
	{
		List<Passenger> bookings=bookingRepository.getAllPassengerInABooking(id);
		if(!bookings.isEmpty())
		{
			return bookings;
		}
		else
		{
			throw new NoRecordAvailableException("No booking available ");
		}
	}
	public Payment getPaymentOfBooking(Integer id)
	{
		Optional<Payment> opt=bookingRepository.getPaymentOfBooking(id);
		if(opt.isPresent())
		{
			return opt.get();
		}
		else
		{
			throw new IdNotFoundException("id not present in db");
		}
	}
	
	public Booking updateBookingStatus(Integer id,Bookingstatus status)
	{
		Optional<Booking> opt=bookingRepository.findById(id);
		if(opt.isEmpty())
		{
			throw new NoRecordAvailableException("No record in db");
		}
		Booking booking=opt.get();
		booking.setStatus(status);
		return bookingRepository.save(booking);
	}
	
	public Booking deleteBooking(Integer id)
	{
		Optional<Booking> opt=bookingRepository.findById(id);
		if(opt.isPresent())
		{
			bookingRepository.delete(opt.get());
			return opt.get();
		}
		else
		{
			throw new NoRecordAvailableException("No Record in booking");
		}
	}
	
	public Page<Booking> getBookingsByPaginationAndSorting(int pn,int ps,String field)
	{
		Page<Booking> bookings=bookingRepository.findAll(PageRequest.of(pn, ps, Sort.by(field).ascending()));
		if(!bookings.isEmpty())
		{
			return bookings;
		}
		else
		{
			throw new NoRecordAvailableException("No record in db");
		}
	}
}

