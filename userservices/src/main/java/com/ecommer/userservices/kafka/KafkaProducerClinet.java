package com.ecommer.userservices.kafka;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerClinet {
    private final KafkaTemplate<String,String> kafkaTemplate;
@Autowired
    public KafkaProducerClinet(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message){
        System.out.println("hello kafka"+ " i am running");
        kafkaTemplate.send(topic,message);
    }
}
