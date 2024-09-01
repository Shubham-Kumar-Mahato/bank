package com.BankingApplication.serviceImpl;


import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private String secretKey="";
	
	public JwtService() {
		try {
		KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");
		SecretKey secret=keyGenerator.generateKey();
		secretKey = Base64.getEncoder().encodeToString(secret.getEncoded());
		
		
		}
		catch(NoSuchAlgorithmException e) {
			throw new RuntimeException();
		}
	}
	
	
	public String generateToken(String username) {
		Map<String , Object> claims=new HashMap<String, Object>();
		
		return Jwts.builder().claims()
		 .add(claims)
		 .subject(username)
		 .issuedAt(new Date(System.currentTimeMillis()))
		 .expiration(new Date(System.currentTimeMillis()+60*60*30))
		 .and()
		 .signWith(getKey())
		 .compact();
	}

	private SecretKey getKey() {
    byte [] key=Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(key);
	}


	public String extractUsername(String token) {
	return extractClaims(token,Claims::getSubject);
	}


	private <T>T extractClaims(String token, Function<Claims,T> claimResolver) {
	
		final Claims claims=extractAllClaims(token);
		return claimResolver.apply(claims);
		
	}


	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public boolean validateToken(String token ,UserDetails userDetails) {
		
		final String username=extractUsername(token);
		return (username.equals(userDetails.getUsername())  && !isTokenExpired(token));
	}


	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractExpiration(token).before(new Date());
	}


    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

}
