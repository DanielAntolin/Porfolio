package com.danielantolin.portfolio.service;

import com.danielantolin.portfolio.dto.ContactRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactEmailService {

    private final JavaMailSender mailSender;
    private final String recipientEmail;

    public ContactEmailService(JavaMailSender mailSender, @Value("${contact.recipient-email:}") String recipientEmail) {
        this.mailSender = mailSender;
        this.recipientEmail = recipientEmail;
    }

    public void send(ContactRequestDto request) {
        if (recipientEmail.isBlank()) {
            throw new IllegalStateException("Contact email is not configured");
        }

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientEmail);
        email.setFrom(recipientEmail);
        email.setReplyTo(request.email());
        email.setSubject("Nuevo mensaje del portfolio: " + request.name());
        email.setText("Nombre: " + request.name() + "\n"
                + "Email: " + request.email() + "\n\n"
                + request.message());
        mailSender.send(email);
    }
}
