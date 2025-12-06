package com.flightBooking.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flightBooking.entity.Payment;
import com.flightBooking.entity.modeOfTransaction;
import com.flightBooking.entity.paymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Integer>
{
	public List<Payment> findByStatus(paymentStatus status);
	
	public List<Payment> findByModeOfTransaction(modeOfTransaction modeOfTransaction);
	
	@Query("SELECT p FROM Payment p WHERE p.booking.id = :id")
	public List<Payment> findByBooking(Integer id);
	
	public List<Payment> findByAmountGreaterThan(Double amount);

}

 
