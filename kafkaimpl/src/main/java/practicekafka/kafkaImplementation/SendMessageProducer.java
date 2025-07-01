package practicekafka.kafkaImplementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import practicekafka.kafkaImplementation.dto.SendEmailResponseDto;

@Component
public class  SendMessageProducer {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics = "sendemail",groupId = "emailService")
    public void handleMessage(String message) {
        try {
            SendEmailResponseDto responseDto = objectMapper.readValue(message, SendEmailResponseDto.class);
            System.out.println("Received email data: " + responseDto);

            // Your email sending logic here (using JavaMailSender or other service)
            sendEmail(responseDto);
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing message: " + e.getMessage());
        }
    }

    private void sendEmail(SendEmailResponseDto emailData) {
        // Assuming you have a configured JavaMailSender
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(emailData.getFrom());
        email.setTo(emailData.getTo());
        email.setSubject(emailData.getSubject());
        email.setText(emailData.getBody());

        // Send the email
        mailSender.send(email);
        System.out.println("email send to "+ emailData.getTo());
    }
}
