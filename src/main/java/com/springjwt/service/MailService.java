package com.springjwt.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private final JavaMailSender mailSender;


    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void sendResetPasswordMailUsingTemplate(String to, String token) throws MessagingException {
        String subject = "Request to reset you password!";
        String body = "Hi " + to + ",<br><br>" +
                "There was a request to change your password!<br><br>" +
                "If you did not make this request then please ignore this email.<br><br>" +
                "Otherwise, please click this link to change your password: <a href='http://localhost:9091/resetPassword?token=" + token + "'> Reset Password </a><br>" +
                "<br>" +
                "<br>" +
                "<br>" +
                "<b>Note:</b> This link will expire in an hour.";


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(body, true);
        helper.setTo(to);
        helper.setSubject(subject);
        mailSender.send(mimeMessage);
    }

    public void sendEmail(String to, String subject, String htmlMsg, String from) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(htmlMsg, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(from);
        mailSender.send(mimeMessage);

    }
}
