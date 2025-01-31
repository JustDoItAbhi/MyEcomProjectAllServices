package paymentservice.customiseloginforemail;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEventListener {

    private final KafkaProducerClinet kafkaProducer;//KAFKA CLIENT DECLARETION
    private final ObjectMapper objectMapper;//DEFAULT OBJECT MAPPER
// DEPENDENCY INJECTION
    public AuthenticationEventListener(KafkaProducerClinet kafkaProducer, ObjectMapper objectMapper) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
    }

    @EventListener // EVENT MANAGMENT
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        // Extract user details from the event
        String username = event.getAuthentication().getName();
        SendEmailDto emailDto = new SendEmailDto();
        emailDto.setFrom("no-reply@example.com");// EMAIL SENDER
        emailDto.setTo(username);  // EMAIL RECIVER
        emailDto.setSubject("Login Successful");// SUBJECT OF EMAIL
        emailDto.setBody("Welcome back, " + username);// OBJECT

        // Send the notification
        try {
            kafkaProducer.sendMessage("sendemail", objectMapper.writeValueAsString(emailDto));
        } catch (IOException e) {
            e.printStackTrace(); // Handle error properly
        }
    }
}

