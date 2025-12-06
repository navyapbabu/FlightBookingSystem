package com.flightBooking.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
 
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightBooking.Dao.BookingDao;
import com.flightBooking.Dto.ResponseStructure;
import com.flightBooking.Exception.*;
import com.flightBooking.entity.*;
import com.flightBooking.repository.FlightRepository;

import jakarta.transaction.Transactional;

@Service
public class BookingService 
{
	@Autowired
	private BookingDao bookingDao;
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Transactional
	public ResponseEntity<ResponseStructure<Booking>> createBooking(Booking booking) throws MissingInformationException, RecordAlreadyExistsException
	{
		ResponseStructure<Booking> response=new ResponseStructure<Booking>();
		if(booking.getFlight() == null || booking.getFlight().getId() == null)
		{
			throw new IdNotFoundException("Flight Id must be provided");
		}
		Optional<Flight> opt=flightRepository.findById(booking.getFlight().getId());
		Flight flight;
		if(opt.isPresent())
		{
			flight=opt.get();
		}
		else
		{
			throw new NoRecordAvailableException("Flight with id : "+booking.getFlight().getId()+"is not present in db");
		}
		booking.setFlight(flight);
		if(booking.getPassenger()==null||booking.getPassenger().isEmpty())
		{
			throw new MissingInformationException("Passenger Information must be provided");
		}
		for(Passenger passenger:booking.getPassenger())
		{
			passenger.setBooking(booking);
		}
		int passengerCount=booking.getPassenger().size();
		int seatCount=flight.getTotalSeats();
		if(passengerCount>seatCount)
		{
			throw new MissingInformationException("number of passengers : "+passengerCount+"  is greater than the number of seat available in : "+flight.getAirline());
		}
		if(booking.getPayment()==null)
		{
			throw new MissingInformationException("Payment information is to be provided");
		}
		double totalPrice=flight.getPrice()*passengerCount;
		flight.setTotalSeats(seatCount-passengerCount);
		Payment payment= booking.getPayment();
		payment.setBooking(booking);
		payment.setAmount(totalPrice);
		payment.setModeOfTransaction(booking.getPayment().getModeOfTransaction());
		payment.setStatus(booking.getPayment().getStatus());
		Booking createBooking=bookingDao.createBooking(booking);
		if(createBooking!=null &&createBooking.getId()!=null)
		{
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("Booking created successfully");
			response.setData(createBooking);
			return new ResponseEntity<ResponseStructure<Booking>>(response,HttpStatus.CREATED);
		}
		else
		{
			throw new RecordAlreadyExistsException("Booking with id : "+booking.getId()+" already exists");
		}
	}
	
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBookings()
	{
		ResponseStructure<List<Booking>> response=new ResponseStructure<List<Booking>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Booking record Fetched");
		response.setData(bookingDao.getAllBookings());
		return new ResponseEntity<ResponseStructure<List<Booking>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(Integer id)
	{
		ResponseStructure<Booking> response=new ResponseStructure<Booking>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Booking record Fetched");
		response.setData(bookingDao.getBookingById(id));
		return new ResponseEntity<ResponseStructure<Booking>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByFlight(String airline)
	{
		ResponseStructure<List<Booking>> response=new ResponseStructure<List<Booking>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Booking record Fetched with "+airline);
		response.setData(bookingDao.getBookingByFlight(airline));
		return new ResponseEntity<ResponseStructure<List<Booking>>>(response,HttpStatus.OK);
	}
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByDate(LocalDate date)
	{
		ResponseStructure<List<Booking>> response=new ResponseStructure<List<Booking>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Booking record Fetched with "+date);
		response.setData(bookingDao.getBookingByDate(date));
		return new ResponseEntity<ResponseStructure<List<Booking>>>(response,HttpStatus.OK);
	}
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingByStatus(Bookingstatus status)
	{
		ResponseStructure<List<Booking>> response=new ResponseStructure<List<Booking>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Booking record Fetched with status : "+status);
		response.setData(bookingDao.getBookingByStatus(status));
		return new ResponseEntity<ResponseStructure<List<Booking>>>(response,HttpStatus.OK);
	}
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengersInABooking(Integer id)
	{
		ResponseStructure<List<Passenger>> response=new ResponseStructure<List<Passenger>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Passenger in a Booking Fetched");
		response.setData(bookingDao.getAllPassengersInABooking(id));
		return new ResponseEntity<ResponseStructure<List<Passenger>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Payment>> getPaymentOfBooking(Integer id)
	{
		ResponseStructure<Payment> response=new ResponseStructure<Payment>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Booking record Fetched");
		response.setData(bookingDao.getPaymentOfBooking(id));
		return new ResponseEntity<ResponseStructure<Payment>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Booking>> updateBookingStatus(Integer id,Bookingstatus status)
	{
		ResponseStructure<Booking> response=new ResponseStructure<Booking>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Booing Status updated");
		response.setData(bookingDao.updateBookingStatus(id,status));
		return new ResponseEntity<ResponseStructure<Booking>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Booking>> deleteBooking(Integer id)
	{
		ResponseStructure<Booking> response=new ResponseStructure<Booking>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Booing record Deleted");
		response.setData(bookingDao.deleteBooking(id));
		return new ResponseEntity<ResponseStructure<Booking>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Page<Booking>>> getBookingsByPaginationAndSorting(Integer pn,Integer ps,String field)
	{
		ResponseStructure<Page<Booking>> response=new ResponseStructure<Page<Booking>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Matching Records Retrived");
		response.setData(bookingDao.getBookingsByPaginationAndSorting(pn, ps, field));
		return new ResponseEntity<ResponseStructure<Page<Booking>>>(response,HttpStatus.OK);
	}
	
	
}
