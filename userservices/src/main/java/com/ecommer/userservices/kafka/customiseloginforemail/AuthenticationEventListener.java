package com.ecommer.userservices.kafka.customiseloginforemail;

import com.ecommer.userservices.entity.Users;
import com.ecommer.userservices.kafka.KafkaProducerClinet;
import com.ecommer.userservices.kafka.SendEmailDto;
import com.ecommer.userservices.security.auth2server.customization.CustomiseGrandAuthority;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEventListener {

    private final KafkaProducerClinet kafkaProducer;
    private final ObjectMapper objectMapper;

    public AuthenticationEventListener(KafkaProducerClinet kafkaProducer, ObjectMapper objectMapper) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        // Extract user details from the event

        String username = event.getAuthentication().getName();
        SendEmailDto emailDto = new SendEmailDto();
        emailDto.setFrom("no-reply@example.com");
        emailDto.setTo(username);  // Assuming the username is the user's email
        emailDto.setSubject("Login Successful");
        emailDto.setBody("Welcome back, " + username);

        // Send the notification (e.g., Kafka message)
        try {
            kafkaProducer.sendMessage("sendemail", objectMapper.writeValueAsString(emailDto));
        } catch (IOException e) {
            e.printStackTrace(); // Handle error properly
        }
    }
}

