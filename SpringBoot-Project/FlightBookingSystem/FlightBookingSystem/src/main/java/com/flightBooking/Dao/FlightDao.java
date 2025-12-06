package com.flightBooking.Dao;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightBooking.Exception.*;
import com.flightBooking.entity.Flight;
import com.flightBooking.repository.FlightRepository;


@Repository
public class FlightDao
{
	@Autowired
	private FlightRepository flightRepository;
	
	public Flight addFlight(Flight flight)
	{
		return flightRepository.save(flight);
	}
	
	public List<Flight> getAllFlights()
	{
		return flightRepository.findAll();
	}
	
	public Flight getFlightById(Integer id)
	{
		Optional<Flight> opt= flightRepository.findById(id);
		if(opt.isPresent())
		{
			return opt.get();
		}
		else
		{
			throw new IdNotFoundException(id+ ": id not existing in DB");
		}
	}
	public List<Flight> getFlightsBySourceAndDestination(String source,String destination)
	{
		List<Flight> flights=flightRepository.findBySourceAndDestination(source, destination);
		if(!flights.isEmpty())
		{
			return flights;
		}
		else
		{
			throw new NoRecordAvailableException("No flights from "+source+" to "+destination);
			
		}
	}
	
	public List<Flight> getFlightsByAirline(String airline)
	{
		List<Flight> flights=flightRepository.findByAirline(airline);
		if(!flights.isEmpty())
		{
			return flights;
		}
		else
		{
			throw new NoRecordAvailableException("No flights from this airline "+airline);
			
		}
		
	}
	
	public Flight updateFlight(Flight flight)
	{
		if(flight.getId()==null)
		{
			throw new IdNotFoundException("Pass the flight id in request body");
		}
		Optional<Flight> opt=flightRepository.findById(flight.getId());
		if(opt.isPresent())
		{
			flightRepository.save(flight);
			return opt.get();
		}
		else
		{
			throw new NoRecordAvailableException("Id Not Existing in db");
		}
	}
	
	public Flight deleteFlight(Integer id)
	{
		Optional<Flight> opt=flightRepository.findById(id);
		if(opt.isPresent())
		{
			flightRepository.delete(opt.get());
			return opt.get();
		}
		else
		{
			throw new NoRecordAvailableException("Id Not Existing in db");
		}
	}
	
	public Page<Flight> getFlightByPaginationAndSorting(int pn,int ps,String field)
	{
		Page<Flight> flights=flightRepository.findAll(PageRequest.of(pn, ps, Sort.by(field).ascending()));
		if(!flights.isEmpty())
		{
			return flights;
		}
		else
		{
			throw new NoRecordAvailableException("No record in db");
		}
	}
	
}
