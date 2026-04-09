package com.dozie.authsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${unosend.from.email}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String toEmail, String otp) {
        String htmlContent = "<div style='font-family: Arial, sans-serif; text-align: center; color: #333;'>"
                           + "<h1>Cassava Authentication</h1>"
                           + "<p>You requested to verify your account or reset your password.</p>"
                           + "<p>Your 6-digit OTP code is:</p>"
                           + "<h2 style='background-color: #f4f4f4; padding: 10px; display: inline-block; border-radius: 5px; color: #000; letter-spacing: 5px;'>" + otp + "</h2>"
                           + "<p>This code will expire in exactly 10 minutes.</p>"
                           + "</div>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Cassava Sign-in Verification Code");
            helper.setText(htmlContent, true);
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
