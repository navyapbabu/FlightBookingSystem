package com.flightBooking.Controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.flightBooking.Dto.ResponseStructure;
import com.flightBooking.Service.FlightServices;
import com.flightBooking.entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/flight")
public class FlightController
{
	@Autowired
	private FlightServices flightService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Flight>> saveFlight(@RequestBody Flight flight)
	{
		return flightService.addFlight(flight);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlights()
	{
		return flightService.getAllFlights();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(@PathVariable Integer id)
	{
		return flightService.getFlightById(id);
		
	}
	@GetMapping("/{source}/{destination}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightsBySourceAndDestination(@PathVariable String source,@PathVariable  String destination)
	{
		return flightService.getFlightsBySourceAndDestination(source, destination);
	}
	
	@GetMapping("/airline/{airline}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightsByAirline(@PathVariable String airline)
	{
		return flightService.getFlightsByAirline(airline);
	}
	@PutMapping
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(@RequestBody Flight flight)
	{
		return flightService.updateFlight(flight);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> deleteFlight(@PathVariable Integer id)
	{
		return flightService.deleteFlight(id);
	}
	
	@GetMapping("/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Flight>>> getBookByPaginationAndSorting(@PathVariable Integer pageNumber,@PathVariable Integer pageSize,@PathVariable String field )
	{
		return flightService.getFlightByPaginationAndSorting(pageNumber, pageSize, field);
	}
}
