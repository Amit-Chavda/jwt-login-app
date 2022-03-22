package com.springjwt.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null) {
			throw new UsernameNotFoundException("User with username " + null + " not found");
		}
		UserDetails tony = User.builder().username("tony").password(new BCryptPasswordEncoder().encode("tony123"))
				.roles("ADMIN").build();

		UserDetails bob = User.builder().username("bob").password(new BCryptPasswordEncoder().encode("bob123"))
				.roles("customer").build();

		switch (username) {
		case "tony":
			return tony;
		case "bob":
			return bob;

		default:
			break;
		}

		if (username.equals(tony.getUsername())) {
			return tony;
		}

		throw new UsernameNotFoundException("User with username " + username + " not found");
	}

}
