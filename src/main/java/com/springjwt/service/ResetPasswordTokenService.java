package com.springjwt.service;

import com.springjwt.entity.ResetPasswordToken;
import com.springjwt.repo.ResetPasswordTokenRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResetPasswordTokenService {

    private ResetPasswordTokenRepository passwordTokenRepository;

    public ResetPasswordTokenService(ResetPasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }

    public ResetPasswordToken findByUsername(String username) throws Exception {
        Optional<ResetPasswordToken> userOptional = passwordTokenRepository.findByUser(username);
        if (userOptional.isEmpty()) {
            throw new Exception("Reset Token for user " + username + " not found!");
        }
        return userOptional.get();
    }

    public ResetPasswordToken save(ResetPasswordToken resetPasswordToken) {
        return passwordTokenRepository.save(resetPasswordToken);
    }

    public ResetPasswordToken findByToken(String token) throws Exception {
        Optional<ResetPasswordToken> resetPasswordToken = passwordTokenRepository.findByToken(token);

        if (resetPasswordToken.isEmpty()) {
            throw new UsernameNotFoundException("Token not found");
        }
        return resetPasswordToken.get();
    }
}
