package com.acker.busticketbackend.auth;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Password Reset Request for Your Account");
        helper.setText("<html><body>Dear User,<br><br>We received a request to reset your password for your account. If you made this request, please enter the OTP given below to reset your password. If you did not request a password reset, please ignore this email.<br><br><b>OTP To reset your password:</b> " + otp + "</body></html>", true);

        mailSender.send(message);
    }
}
