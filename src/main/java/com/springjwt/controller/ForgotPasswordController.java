package com.springjwt.controller;

import com.springjwt.entity.ResetPasswordToken;
import com.springjwt.entity.User;
import com.springjwt.service.MailService;
import com.springjwt.service.ResetPasswordTokenService;
import com.springjwt.service.UserService;
import com.springjwt.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Controller
public class ForgotPasswordController {

    private final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);
    private UserService userService;
    private MailService mailService;
    private ResetPasswordTokenService resetPasswordTokenService;

    public ForgotPasswordController(UserService userService, MailService mailService, ResetPasswordTokenService resetPasswordTokenService) {
        this.userService = userService;
        this.mailService = mailService;
        this.resetPasswordTokenService = resetPasswordTokenService;
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    @ResponseBody
    public String forgotPassword(@ModelAttribute(name = "username") String username) {
        try {
            //check if user exists
            User user = userService.findByUsername(username);
            //generate token and send link to reset password
            ResetPasswordToken resetPasswordToken = TokenUtil.generateResetPasswordTokenForUser(user);
            //save token in database
            resetPasswordTokenService.save(resetPasswordToken);
            //send reset link
            mailService.sendResetPasswordMailUsingTemplate(username, resetPasswordToken.getToken());

            return "<h2>" +
                    "<p style=\"text-align:center\">Please, go to you mails for further steps!</p>" +
                    "</h2>";
        } catch (UsernameNotFoundException usernameNotFoundException) {
            logger.error(usernameNotFoundException.getMessage());
            return "<h2 style=\"text-align:center\">User does not exists with " + username + "!</h2>";
        } catch (MessagingException exception) {
            logger.error(exception.getMessage());
            return "<h2 style=\"text-align:center\">User does not exists with " + exception.getMessage() + "!</h2>";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "<h2 style=\"text-align:center\">Something went wrong!</h2>";
        }
    }
}
