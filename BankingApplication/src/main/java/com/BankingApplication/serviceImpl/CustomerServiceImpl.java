package com.BankingApplication.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BankingApplication.payload.Customer;
import com.BankingApplication.repository.CustomerRepository;
import com.BankingApplication.service.CustomerService;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Customer createCustomer(Customer customer) {
      
		
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		Customer customer2=this.customerRepository.save(customer);
		
		return customer2;
		
	}

	@Override
	public List<Customer> getAllCustomers() {
		
		
		List<Customer> list=customerRepository.findAll();
		
		return list;
	}

	@Override
	public void deleteCustomer(int id) {
		
		try {
		Customer customer=customerRepository.findById(id);
			
		} catch (Exception e) {
			
			 e.printStackTrace();
			// TODO: handle exception
		}
		
	}
	
	
	

}
