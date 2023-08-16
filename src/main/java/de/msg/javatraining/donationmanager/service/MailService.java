/*
package de.msg.javatraining.donationmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendWelcomeEmail(String recipientEmail, String initialPassword) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("Welcome to Our Application");
        mailMessage.setText("Welcome to our application! Your initial password is: " + initialPassword);

        javaMailSender.send(mailMessage);
    }
}
*/
