//package com.flightBooking.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.flightBooking.Dao.BookingDao;
//import com.flightBooking.Dao.PaymentDao;
//import com.flightBooking.Dto.ResponseStructure;
//import com.flightBooking.entity.Booking;
//import com.flightBooking.entity.Payment;
//
//@Service
//public class PaymentServices {
//
//    @Autowired
//    private PaymentDao paymentDao;
//
//    @Autowired
//    private BookingDao bookingDao;
//
//    public ResponseEntity<ResponseStructure<Payment>> createPayment(Payment payment) {
//
//        Booking booking1 = bookingDao.getBooking();
//        payment.setBooking(booking1);
//        booking1.setPayment(payment);
//
//        ResponseStructure<Payment> response = new ResponseStructure<>();
//        response.setStatuscode(HttpStatus.CREATED.value());
//        response.setMessage("Payment added successfully");
//        response.setData(paymentDao.createPayment(payment));
//
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//	
//}
//
//
//
//
package com.flightBooking.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightBooking.Dao.PaymentDao;
import com.flightBooking.Dto.ResponseStructure;
import com.flightBooking.Exception.*;
import com.flightBooking.entity.Payment;
import com.flightBooking.entity.modeOfTransaction;
import com.flightBooking.entity.paymentStatus;

@Service
public class PaymentService
{
	@Autowired
	private PaymentDao paymentDao;
	
	public ResponseEntity<ResponseStructure<Payment>> recordPayment(Payment payment) throws RecordAlreadyExistsException
	{
		ResponseStructure<Payment> response=new ResponseStructure<Payment>();
		if(payment.getId() != null)
		{
	        Optional<Payment> opt= paymentDao.getPaymentById(payment.getId());
	        if(opt.isPresent())
	        {
	            throw new RecordAlreadyExistsException("The Payment record with ID: " + payment.getId() + ", already exists");
	        }
	    }
		 Payment savedPayment = paymentDao.recordPayment(payment);
		 response.setStatusCode(HttpStatus.CREATED.value());
		 response.setMessage("New Payment record saved");
		 response.setData(savedPayment);

		 return new ResponseEntity<ResponseStructure<Payment>>(response, HttpStatus.CREATED);
	}
	public ResponseEntity<ResponseStructure<List<Payment>>> getAllPayment()
	{
		ResponseStructure<List<Payment>> response=new ResponseStructure<List<Payment>>();
		response.setStatusCode(HttpStatus.OK.value());
		 response.setMessage("payments fetched");
		 response.setData(paymentDao.getAllPAyment());
		 return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
	}
	public ResponseEntity<ResponseStructure<Payment>> getPaymentById(Integer id)
	{
		ResponseStructure<Payment> response=new ResponseStructure<Payment>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("payment fetched");
		Optional<Payment> opt=paymentDao.getPaymentById(id);
		response.setData(opt.get());
		return new ResponseEntity<ResponseStructure<Payment>>(response,HttpStatus.OK);
	}
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByStatus(paymentStatus status)
	{
		ResponseStructure<List<Payment>> response=new ResponseStructure<List<Payment>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Payments record Fetched with status : "+status);
		response.setData(paymentDao.getPaymentByStatus(status));
		return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByModeOfTransaction(modeOfTransaction mode)
	{
		ResponseStructure<List<Payment>> response=new ResponseStructure<List<Payment>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Payments record Fetched with ModeOfTransaction : "+mode);
		response.setData(paymentDao.getPaymentByModeOfTransaction(mode));
		return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentByBooking(Integer id)
	{
		ResponseStructure<List<Payment>> response=new ResponseStructure<List<Payment>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("All Payments record Fetched eith booking ");
		response.setData(paymentDao.getPaymentByBooking(id));
		return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Payment>> updatePaymentStatus(Integer id,paymentStatus status)
	{
		ResponseStructure<Payment> response=new ResponseStructure<Payment>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Payment Status updated");
		response.setData(paymentDao.updatePaymentStatus(id,status));
		return new ResponseEntity<ResponseStructure<Payment>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Payment>>> getPaymentGreaterThan(Double amount)
	{
		ResponseStructure<List<Payment>> response=new ResponseStructure<List<Payment>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Payments greater than : "+amount);
		response.setData(paymentDao.getPaymentGreaterThan(amount));
		return new ResponseEntity<ResponseStructure<List<Payment>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Page<Payment>>> getPaymentsByPaginationAndSorting(Integer pn,Integer ps,String field)
	{
		ResponseStructure<Page<Payment>> response=new ResponseStructure<Page<Payment>>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Matching Records Retrived");
		response.setData(paymentDao.getPaymentsByPaginationAndSorting(pn, ps, field));
		return new ResponseEntity<ResponseStructure<Page<Payment>>>(response,HttpStatus.OK);
	}
}




