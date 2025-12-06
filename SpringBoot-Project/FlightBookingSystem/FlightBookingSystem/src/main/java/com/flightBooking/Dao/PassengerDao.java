package com.flightBooking.Dao;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightBooking.Exception.IdNotFoundException;
import com.flightBooking.Exception.NoRecordAvailableException;
import com.flightBooking.entity.Passenger;
import com.flightBooking.repository.PassengerRepository;

@Repository
public class PassengerDao
{
	@Autowired
	private PassengerRepository passengerRepository;
	
	public Passenger addPassenger(Passenger passenger)
	{
		return passengerRepository.save(passenger);
	}
	
	public Optional<Passenger> getPassengerById(Integer id)
	{
		return passengerRepository.findById(id);
	}
	
	public List<Passenger> getAllPassenger()
	{
		List<Passenger> passengers=passengerRepository.findAll();
		if(!passengers.isEmpty())
		{
			return passengers;
		}
		else
		{
			throw new NoRecordAvailableException("passenger is empty");
		}
	}
	
	public Passenger getPassengerByContectNo(Long contactNo)
	{
		Optional<Passenger> opt=passengerRepository.findByContactNo(contactNo);
		if(opt.isEmpty())
		{
			throw new NoRecordAvailableException("passenger is empty");
		}
		else
		{
			return opt.get();
		}
	}
	
	public Passenger updatePassenger(Passenger passenger)
	{
		if(passenger.getId()==null)
		{
			throw new IdNotFoundException("Pass the flight id in request body");
		}
		Optional<Passenger> opt=passengerRepository.findById(passenger.getId());
		if(opt.isPresent())
		{
			passengerRepository.save(passenger);
			return opt.get();
		}
		else
		{
			throw new NoRecordAvailableException("passenger is empty");
		}
	}
	
	public List<Passenger> getPassengersByFlight(Integer id)
	{
		List<Passenger> passengers=passengerRepository.getPassengersByFlight(id);
		if(passengers.isEmpty()||passengers==null)
		{
			throw new NoRecordAvailableException("No flight record ");
		}
		else
		{
			return passengers;
		}
		
	}
	
	public Page<Passenger> getPassengerByPaginationAndSorting(int pn,int ps,String field)
	{
		Page<Passenger> passengers=passengerRepository.findAll(PageRequest.of(pn, ps, Sort.by(field).ascending()));
		if(!passengers.isEmpty())
		{
			return passengers;
		}
		else
		{
			throw new NoRecordAvailableException("No record in db");
		}
	}
}
