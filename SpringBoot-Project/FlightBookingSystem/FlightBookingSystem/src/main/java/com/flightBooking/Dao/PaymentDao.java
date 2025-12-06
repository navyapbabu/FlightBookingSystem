package com.flightBooking.Dao;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.flightBooking.Exception.NoRecordAvailableException;
import com.flightBooking.entity.Payment;
import com.flightBooking.entity.modeOfTransaction;
import com.flightBooking.entity.paymentStatus;
import com.flightBooking.repository.PaymentRepository;

@Repository

public class PaymentDao {
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	public Payment recordPayment(Payment payment)
	{
		return paymentRepository.save(payment);
	}
	public Optional<Payment> getPaymentById(Integer id)
	{
		return paymentRepository.findById(id);
	}
	public List<Payment> getAllPAyment()
	{
		List<Payment> payments=paymentRepository.findAll();
		if(payments.isEmpty())
		{
			throw new NoRecordAvailableException("No record in the database");
		}
		else
		{
			return payments;
		}
	}
	
	public List<Payment> getPaymentByStatus(paymentStatus status)
	{
		List<Payment> payments=paymentRepository.findByStatus(status);
		if(!payments.isEmpty())
		{
			return payments;
		}
		else
		{
			throw new NoRecordAvailableException("No Payments available with status : "+status);
		}
	}
	
	public List<Payment> getPaymentByModeOfTransaction(modeOfTransaction mode)
	{
		List<Payment> payments=paymentRepository.findByModeOfTransaction(mode);
		if(!payments.isEmpty())
		{
			return payments;
		}
		else
		{
			throw new NoRecordAvailableException("No Payments available with mode of transcation : "+mode);
		}
	}
	
	public List<Payment> getPaymentByBooking(Integer id)
	{
		List<Payment> payments=paymentRepository.findByBooking(id);
		if(!payments.isEmpty())
		{
			return payments;
		}
		else
		{
			throw new NoRecordAvailableException("No Payments available for thsi booking ");
		}
	}
	
	public Payment updatePaymentStatus(Integer id,paymentStatus status)
	{
		Optional<Payment> opt=paymentRepository.findById(id);
		if(opt.isEmpty())
		{
			throw new NoRecordAvailableException("No record in db");
		}
		Payment payment=opt.get();
		payment.setStatus(status);
		return paymentRepository.save(payment);
	}
	
	public List<Payment> getPaymentGreaterThan(Double amount)
	{
		List<Payment> payment=paymentRepository.findByAmountGreaterThan(amount);
		if(payment.isEmpty())
		{
			throw new NoRecordAvailableException("No record in db");
		}
		else
		{
			return payment;
		}
	}
	
	public Page<Payment> getPaymentsByPaginationAndSorting(int pn,int ps,String field)
	{
		Page<Payment> payments=paymentRepository.findAll(PageRequest.of(pn, ps, Sort.by(field).ascending()));
		if(!payments.isEmpty())
		{
			return payments;
		}
		else
		{
			throw new NoRecordAvailableException("No record in db");
		}
	}
}

