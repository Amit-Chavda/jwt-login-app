package com.springjwt.util;

import com.springjwt.entity.ResetPasswordToken;
import com.springjwt.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class TokenUtil {

    private TokenUtil() {
        throw new IllegalStateException("Token Utility class");
    }

    public static ResetPasswordToken generateResetPasswordTokenForUser(User user) {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(UUID.randomUUID().toString());
        resetPasswordToken.setExpiry(LocalDateTime.now().plusHours(2));
        resetPasswordToken.set_createdAt(LocalDateTime.now());
        resetPasswordToken.setUser(user);
        return resetPasswordToken;
    }

    public static boolean isExpired(ResetPasswordToken resetPasswordToken) {
        return resetPasswordToken.getExpiry().isBefore(LocalDateTime.now());
    }
}
