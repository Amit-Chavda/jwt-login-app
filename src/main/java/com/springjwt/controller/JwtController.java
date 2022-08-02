package com.springjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springjwt.entity.JwtRequest;
import com.springjwt.util.JwtUtil;
import com.springjwt.service.CustomUserDetailsService;

@RestController
public class JwtController {

	@Autowired
	CustomUserDetailsService userDetailsService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;

	@RequestMapping("/token2")
	private ResponseEntity<String> generateToken(@RequestBody JwtRequest jwtRequest) {

		System.out.println("hello world");
		try {

			System.out.println(jwtRequest.getUsername() + " : " + jwtRequest.getPassword());
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (Exception e) {
			throw e;
		}

		UserDetails user = userDetailsService.loadUserByUsername(jwtRequest.getUsername());

		return ResponseEntity.ok(jwtUtil.generateToken(user));

	}
}
