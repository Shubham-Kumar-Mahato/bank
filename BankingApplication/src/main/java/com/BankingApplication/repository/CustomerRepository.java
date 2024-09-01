package com.BankingApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BankingApplication.payload.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	
	Customer findById(int id);

	Customer findByCustomerName(String username);
}
