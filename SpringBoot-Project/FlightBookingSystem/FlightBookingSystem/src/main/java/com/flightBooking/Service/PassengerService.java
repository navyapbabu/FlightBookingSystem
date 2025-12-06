package com.flightBooking.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightBooking.Dao.PassengerDao;
import com.flightBooking.Dto.ResponseStructure;
import com.flightBooking.Exception.NoRecordAvailableException;
import com.flightBooking.Exception.RecordAlreadyExistsException;
import com.flightBooking.entity.Passenger;

@Service
public class PassengerService
{
	@Autowired
	private PassengerDao passengerDao;
	
	public ResponseEntity<ResponseStructure<Passenger>> addPassenger(Passenger passenger) throws RecordAlreadyExistsException
	{
		ResponseStructure<Passenger> response=new ResponseStructure<Passenger>();
		if(passenger.getId()!=null)
		{
			Optional<Passenger> opt=passengerDao.getPassengerById(passenger.getId());
			if(opt.isPresent())
			{
				throw new RecordAlreadyExistsException("The Passenger record with ID: "+passenger.getId()+" already exists");
			}
		}
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("New Passenger record saved");
		response.setData(passengerDao.addPassenger(passenger));
		return new ResponseEntity<ResponseStructure<Passenger>>(response,HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassenger()
	{
		ResponseStructure<List<Passenger>> response=new ResponseStructure<List<Passenger>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All passengers fetched");
		response.setData(passengerDao.getAllPassenger());
		return new ResponseEntity<ResponseStructure<List<Passenger>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerById(Integer id)
	{
		Optional<Passenger> opt=passengerDao.getPassengerById(id);
		ResponseStructure<Passenger> response=new ResponseStructure<Passenger>();
		if(opt.isPresent())
		{
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("passenger details fetched");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<Passenger>>(response,HttpStatus.OK);
		}
		else
		{
			throw new NoRecordAvailableException("No record in db");
		}
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerByContectNo(Long contactNo)
	{
		ResponseStructure<Passenger> response=new ResponseStructure<Passenger>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("passenger with contactnumber : "+contactNo+" fetched");
		response.setData(passengerDao.getPassengerByContectNo(contactNo));
		return new ResponseEntity<ResponseStructure<Passenger>>(response,HttpStatus.OK);
	}
	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(Passenger passenger)
	{
		ResponseStructure<Passenger> response=new ResponseStructure<Passenger>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Passenger details updated");
		response.setData(passengerDao.updatePassenger(passenger));
		return new ResponseEntity<ResponseStructure<Passenger>>(response,HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengersByFlight(Integer id)
	{
		ResponseStructure<List<Passenger>> response=new ResponseStructure<List<Passenger>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All passengers fetched from flight");
		response.setData(passengerDao.getPassengersByFlight(id));
		return new ResponseEntity<ResponseStructure<List<Passenger>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Page<Passenger>>> getPassengerByPaginationAndSorting(Integer pn,Integer ps,String field)
	{
		ResponseStructure<Page<Passenger>> response=new ResponseStructure<Page<Passenger>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Matching Records Retrived");
		response.setData(passengerDao.getPassengerByPaginationAndSorting(pn, ps, field));
		return new ResponseEntity<ResponseStructure<Page<Passenger>>>(response,HttpStatus.OK);
	}
}
