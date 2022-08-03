package com.springjwt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springjwt.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.springjwt.entity.JwtRequest;
import com.springjwt.entity.JwtResponse;
import com.springjwt.util.JwtUtil;
import com.springjwt.service.CustomUserDetailsService;

@org.springframework.stereotype.Controller
public class Controller {
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;


	@RequestMapping("/")
	public String hello() {
		return "home";
	}

	@RequestMapping("/loginError")
	@ResponseBody
	public String error(@RequestParam String er) {
		return "<h2>" + er + "</h2>";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@RequestMapping("/logoutPage")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie c = new Cookie("token", "");
		c.setMaxAge(0);
		response.addCookie(c);
		return "login";
	}
}
