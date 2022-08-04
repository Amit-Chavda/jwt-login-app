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
                return "<h3>" +
                        "<p style=\"text-align:center;color:red;\">Link has expired!" +
                        "</h3><br>" +
                        "<p style=\"text-align:center;\">" +
                        "<a href='http://localhost:9091/forgotPassword'>Try Again</a>" +
                        "</p>";
            }

            User user = resetPasswordToken.getUser();
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            userService.save(user);
            return "<h2>" +
                    "<p style=\"text-align:center;color:green;\">Password reset successfully!</p><br> " +
                    "<p style=\"text-align:center;\">Click <a href='http://localhost:9091/login'>here</a> to login!</p>" +
                    "</h2>";
        } catch (IllegalStateException illegalStateException) {
            logger.error(illegalStateException.getMessage());
            return "<h3>" +
                    "<p style=\"text-align:center;color:red;\">Link has expired or invalid link accessed!" +
                    "</h3>";
        } catch (Exception exception) {
            return "<h2>" +
                    "<p style=\"text-align:center;color:red;\">Something wen wrong!" +
                    "</h2>";
        }
    }
}
