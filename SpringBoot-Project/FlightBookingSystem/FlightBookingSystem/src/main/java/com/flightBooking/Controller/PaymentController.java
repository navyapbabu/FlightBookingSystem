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
import com.flightBooking.Service.PaymentService;
import com.flightBooking.entity.Payment;
import com.flightBooking.entity.modeOfTransaction;
import com.flightBooking.entity.paymentStatus;

@RestController
@RequestMapping("/payment")
public class PaymentController
{
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Payment>> recordPayment(@RequestBody Payment payment) throws RecordAlreadyExistsException
	{
		return paymentService.recordPayment(payment);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayment()
	{
		return paymentService.getAllPayment();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(@PathVariable Integer id)
	{
		return paymentService.getPaymentById(id);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(@PathVariable paymentStatus status)
	{
		return paymentService.getPaymentByStatus(status);
	}
	
	@GetMapping("/mode/{mode}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByModeOfTransaction(@PathVariable modeOfTransaction mode)
	{
		return paymentService.getPaymentByModeOfTransaction(mode);
	}
	
	@GetMapping("/booking/{id}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByBooking(@PathVariable Integer id)
	{
		return paymentService.getPaymentByBooking(id);
	}
	
	@PutMapping("/{id}/{status}")
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(@PathVariable Integer id,@PathVariable paymentStatus status)
	{
		return paymentService.updatePaymentStatus(id, status);
	}
	
	@GetMapping("/gprice/{amount}")
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentGreaterThan(@PathVariable Double amount)
	{
		return paymentService.getPaymentGreaterThan(amount);
	}
	
	@GetMapping("/{pageNumber}/{pageSize}/{field}")
	public ResponseEntity<ResponseStructure<Page<Payment>>> getPaymentsByPaginationAndSorting(@PathVariable Integer pageNumber,@PathVariable Integer pageSize,@PathVariable String field )
	{
		return paymentService.getPaymentsByPaginationAndSorting(pageNumber, pageSize, field);
	}
}
