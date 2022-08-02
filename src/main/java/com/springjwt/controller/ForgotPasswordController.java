package com.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
public class ForgotPasswordController {

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    @ResponseBody
    public String forgotPassword(@RequestBody String email) {
        String token = (new Random().nextInt(500000) * 1000) + "";
        System.out.println("Password Reset token generated : " + token);
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
        System.out.println("Validated token :" +token);
        System.out.println("Password Reset successfully!");
        return "resetPassword";
    }
}
