package com.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

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

	@GetMapping("/logoutPage")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Cookie c = new Cookie("token", "");
		c.setMaxAge(0);
		response.addCookie(c);
		return "login";
	}
}
