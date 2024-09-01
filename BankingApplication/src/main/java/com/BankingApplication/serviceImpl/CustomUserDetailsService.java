package com.BankingApplication.serviceImpl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BankingApplication.payload.Customer;

import com.BankingApplication.repository.CustomerRepository;



@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Customer userCustomer= customerRepository.findByCustomerName(username);
	
	if(userCustomer==null) {
		throw new UsernameNotFoundException("Username not found with name : "+username);
	}
	return new org.springframework.security.core.userdetails.User(userCustomer.getCustomerName(),
			userCustomer.getPassword(),Collections.singletonList(new SimpleGrantedAuthority(userCustomer.getRole())));
			
	}
	
	

}
