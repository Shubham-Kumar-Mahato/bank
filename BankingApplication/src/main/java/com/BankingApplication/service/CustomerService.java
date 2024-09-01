package com.BankingApplication.service;

import java.util.List;

import com.BankingApplication.payload.Customer;

public interface CustomerService {

	
	Customer createCustomer(Customer customer);
	List<Customer> getAllCustomers();
	void deleteCustomer(int id);
}
