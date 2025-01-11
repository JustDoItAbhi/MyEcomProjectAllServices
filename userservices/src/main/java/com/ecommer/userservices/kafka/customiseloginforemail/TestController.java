package com.ecommer.userservices.kafka.customiseloginforemail;

import com.ecommer.userservices.kafka.KafkaProducerClinet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final KafkaProducerClinet kafkaProducer;

    public TestController(KafkaProducerClinet kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/test-producer")
    public String testProducer() {
        kafkaProducer.sendMessage("sendemail", "{\"to\":\"user@example.com\",\"subject\":\"Test\",\"body\":\"Hello Kafka\"}");
        return "Message sent!";
    }
}
