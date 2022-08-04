package com.springjwt.service;

import com.springjwt.entity.User;
import com.springjwt.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);


        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        return userOptional.get();
    }


    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found!");
        }
        return userOptional.get();
    }
}
