package com.springjwt.controller;

import com.springjwt.entity.JwtResponse;
import com.springjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.springjwt.entity.JwtRequest;
import com.springjwt.util.JwtUtil;

@RestController
public class JwtController {

    @Autowired
    UserService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/token")
    public ResponseEntity<JwtResponse> generateToken(@ModelAttribute JwtRequest jwtRequest) {
        JwtResponse response = new JwtResponse();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtRequest.getUsername(),
                        jwtRequest.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        String token = jwtUtil.generateToken(user);
        response.setToken(token);
        response.setExpiryTime(jwtUtil.extractExpiration(token).toString());
        response.setTokenExpired(jwtUtil.isTokenExpired(token));

        return ResponseEntity.ok(response);
    }

}
