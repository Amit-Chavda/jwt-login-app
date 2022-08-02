package com.springjwt.controller;

import com.springjwt.entity.ResetPasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class ForgotPasswordController {
    private List<ResetPasswordToken> tokenList = new ArrayList<>();

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    @ResponseBody
    public String forgotPassword(@RequestBody String email) {
        String token = (new Random().nextInt(500000) * 1000) + "";
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(token);
        resetPasswordToken.setEmail(email);
        resetPasswordToken.setExpiry(LocalDateTime.now().plusHours(2));
        tokenList.add(resetPasswordToken);
        String url = "http://localhost:8080/resetPassword?token=" + token;
        return url;
    }

    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "resetPassword";
    }

    @PostMapping(path = "/resetPassword")
    public String resetPassword(@RequestParam String token) {
        //validate token
        ResetPasswordToken passwordToken = tokenList.stream().filter((t1) -> {
            return t1.getToken().equals(token);
        }).findFirst().orElse(null);

        if (!passwordToken.getExpiry().isBefore(LocalDateTime.now())) {
            System.out.println("Password Reset successfully!");
            return "resetPassword";
        }
        return "login";
    }
}
