package com.BankingApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankingApplication.payload.LoginRequest;
import com.BankingApplication.serviceImpl.CustomUserDetailsService;
import com.BankingApplication.serviceImpl.JwtService;

@RestController
@RequestMapping("/api/customer")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		final UserDetails userDetails=userDetailsService.loadUserByUsername(loginRequest.getUsername());
		
		String token=jwtService.generateToken(userDetails.getUsername());
		return new ResponseEntity<String>(token,HttpStatus.OK);
		
		}
		catch(Exception e) {
			return null;
			
		}
		
		
		
		
	}
	
	
}
