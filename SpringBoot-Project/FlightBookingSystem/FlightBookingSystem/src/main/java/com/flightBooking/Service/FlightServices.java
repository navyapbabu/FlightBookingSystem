package com.flightBooking.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightBooking.Dao.FlightDao;
import com.flightBooking.Dto.ResponseStructure;
import com.flightBooking.entity.Flight;


@Service
public class FlightServices
{
	@Autowired
	private FlightDao flightDao;
	
	public ResponseEntity<ResponseStructure<Flight>> addFlight(Flight flight)
	{
		ResponseStructure<Flight> response=new ResponseStructure<Flight>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight Record Saved");
		response.setData(flightDao.addFlight(flight));
		
		return new ResponseEntity<ResponseStructure<Flight>>(response,HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlights()
	{
		ResponseStructure<List<Flight>> response=new ResponseStructure<List<Flight>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Flight Records Fetched");
		response.setData(flightDao.getAllFlights());
		
		return new ResponseEntity<ResponseStructure<List<Flight>>>(response,HttpStatus.OK);	
	}
	
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(Integer id)
	{
		ResponseStructure<Flight> response=new ResponseStructure<Flight>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Flight with id : "+id+" fetched");
		response.setData(flightDao.getFlightById(id));
		
		return new ResponseEntity<ResponseStructure<Flight>>(response,HttpStatus.OK);
		
	}
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightsBySourceAndDestination(String source,String destination)
	{
		//String result1 = source.substring(0, 1).toUpperCase() + source.substring(1).toLowerCase();
		//String result2 = destination.substring(0, 1).toUpperCase() + destination.substring(1).toLowerCase();
		ResponseStructure<List<Flight>> response=new ResponseStructure<List<Flight>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Records fetched");
		response.setData(flightDao.getFlightsBySourceAndDestination(source, destination));
		
		return new ResponseEntity<ResponseStructure<List<Flight>>>(response,HttpStatus.OK);
	}
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightsByAirline(String airline)
	{
		//String result = airline.substring(0, 1).toUpperCase() + airline.substring(1).toLowerCase();
		ResponseStructure<List<Flight>> response=new ResponseStructure<List<Flight>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Records fetched");
		response.setData(flightDao.getFlightsByAirline(airline));
		
		return new ResponseEntity<ResponseStructure<List<Flight>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(Flight flight)
	{
		ResponseStructure<Flight> response=new ResponseStructure<Flight>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Record has been updated");
		response.setData(flightDao.updateFlight(flight));
		
		return new ResponseEntity<ResponseStructure<Flight>>(response,HttpStatus.CREATED);
	}
	public ResponseEntity<ResponseStructure<Flight>> deleteFlight(Integer id)
	{
		ResponseStructure<Flight> response=new ResponseStructure<Flight>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Record has been deleted");
		response.setData(flightDao.deleteFlight(id));
		
		return new ResponseEntity<ResponseStructure<Flight>>(response,HttpStatus.OK);
	}
	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightByPaginationAndSorting(Integer pn,Integer ps,String field)
	{
		ResponseStructure<Page<Flight>> response=new ResponseStructure<Page<Flight>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Matching Records Retrived");
		response.setData(flightDao.getFlightByPaginationAndSorting(pn, ps, field));
		return new ResponseEntity<ResponseStructure<Page<Flight>>>(response,HttpStatus.OK);
	}
	
}
