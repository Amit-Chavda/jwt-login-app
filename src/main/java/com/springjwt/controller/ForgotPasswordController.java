package com.springjwt.controller;

import com.springjwt.entity.ResetPasswordToken;
import com.springjwt.entity.User;
import com.springjwt.service.ResetPasswordTokenService;
import com.springjwt.service.UserService;
import com.springjwt.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ForgotPasswordController {

    private final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);
    private UserService userService;
    private ResetPasswordTokenService resetPasswordTokenService;

    public ForgotPasswordController(UserService userService, ResetPasswordTokenService resetPasswordTokenService) {
        this.userService = userService;
        this.resetPasswordTokenService = resetPasswordTokenService;
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    @ResponseBody
    public String forgotPassword(@ModelAttribute(name = "username") String username) {
        User user;
        ResetPasswordToken resetPasswordToken = null;
        try {
            user = userService.findByUsername(username);
            resetPasswordToken = TokenUtil.generateResetPasswordTokenForUser(user);
            resetPasswordTokenService.save(resetPasswordToken);
            return "<h2>" +
                    "Click the <a href='http://localhost:9091/resetPassword?token=" + resetPasswordToken.getToken() + "'>link </a>to reset your password!" +
                    "</h2>";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "<h2>Something went wrong!</h2>";
    }
}
