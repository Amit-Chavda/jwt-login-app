package com.springjwt.service;

import com.springjwt.entity.ResetPasswordToken;
import com.springjwt.repo.ResetPasswordTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResetPasswordTokenService {

    private ResetPasswordTokenRepository passwordTokenRepository;

    public ResetPasswordTokenService(ResetPasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }

    public ResetPasswordToken save(ResetPasswordToken resetPasswordToken) {
        return passwordTokenRepository.save(resetPasswordToken);
    }

    public ResetPasswordToken findByToken(String token) throws IllegalStateException {
        Optional<ResetPasswordToken> resetPasswordToken = passwordTokenRepository.findByToken(token);

        if (resetPasswordToken.isEmpty()) {
            throw new IllegalStateException("Invalid token!");
        }
        return resetPasswordToken.get();
    }
}
