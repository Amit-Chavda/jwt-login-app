package com.springjwt.controller;

import com.springjwt.entity.ResetPasswordToken;
import com.springjwt.entity.User;
import com.springjwt.service.ResetPasswordTokenService;
import com.springjwt.service.UserService;
import com.springjwt.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResetPasswordController {

    private final Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);
    private ResetPasswordTokenService resetPasswordTokenService;
    private UserService userService;

    public ResetPasswordController(ResetPasswordTokenService resetPasswordTokenService, UserService userService) {
        this.resetPasswordTokenService = resetPasswordTokenService;
        this.userService = userService;
    }

    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "resetPassword";
    }

    @PostMapping(path = "/resetPassword")
    @ResponseBody
    public String resetPassword(@RequestParam String token, @ModelAttribute(name = "password") String password) {

        try {
            ResetPasswordToken resetPasswordToken = resetPasswordTokenService.findByToken(token);

            //expired token
            if (TokenUtil.isExpired(resetPasswordToken)) {
                return "<h3>Session has expired!</h3><br>" +
                        "<a href='http://localhost:9091/forgotPassword'>Try Again</a>";
            }

            User user = resetPasswordToken.getUser();
            logger.info(password);
            logger.info(user.toString());
            logger.info(resetPasswordToken.toString());
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            userService.save(user);
            return "<h2>" +
                    "Password reset successfully!<br> " +
                    "Click <a href='http://localhost:9091/login'>here</a> to login!" +
                    "</h2>";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "<h3>Something went wrong!</h3>";
    }
}
