package com.ecommer.userservices.kafka.customiseloginforemail;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "sendemail", groupId = "email-service")
    public void listen(String message) {
        System.out.println("Received message from Kafka: " + message);
        // Parse and process the email
    }
}

