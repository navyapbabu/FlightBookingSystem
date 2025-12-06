package com.flightBooking.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightBooking.Dto.ResponseStructure;
import com.flightBooking.Exception.RecordAlreadyExistsException;
import com.flightBooking.Service.PassengerService;
import com.flightBooking.entity.Passenger;


@RestController
@RequestMapping("/passenger")
public class PassengerController
{
	@Autowired
	private PassengerService passengerService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Passenger>> addPassenger(@RequestBody Passenger passenger) throws RecordAlreadyExistsException
	{
		return passengerService.addPassenger(passenger);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassenger()
	{
		return passengerService.getAllPassenger();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerById(@PathVariable Integer id)
	{
		return passengerService.getPassengerById(id);
	}
	
	@GetMapping("/no/{contactNo}")
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerByContactNo(@PathVariable Long contactNo)
	{
		return passengerService.getPassengerByContectNo(contactNo);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(@RequestBody Passenger passenger)
	{
		return passengerService.updatePassenger(passenger);
	}
	
	@GetMapping("/flight/{id}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengersByFlight(@PathVariable Integer id)
	{
		return passengerService.getPassengersByFlight(id);
	}
	
	@GetMapping("/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengerByPaginationAndSorting(@PathVariable Integer pageNumber,@PathVariable Integer pageSize,@PathVariable String field )
	{
		return passengerService.getPassengerByPaginationAndSorting(pageNumber, pageSize, field);
	}
}
