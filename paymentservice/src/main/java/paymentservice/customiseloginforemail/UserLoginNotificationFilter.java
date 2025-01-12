package paymentservice.customiseloginforemail;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class  UserLoginNotificationFilter extends OncePerRequestFilter {
    private final KafkaProducerClinet kafkaProducer;
    private final ObjectMapper objectMapper;

    public UserLoginNotificationFilter(KafkaProducerClinet kafkaProducer, ObjectMapper objectMapper) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated and is a regular login (not an OAuth2 flow)
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            SendEmailDto emailDto = new SendEmailDto();
            emailDto.setFrom("no-reply@example.com");
            emailDto.setTo(username); // Assuming username is the email
            emailDto.setSubject("payment link generated");
            emailDto.setBody("PLEASE PAY , " + username);

            // Send the notification
            kafkaProducer.sendMessage("sendemail", objectMapper.writeValueAsString(emailDto));
        }

        filterChain.doFilter(request, response); // Continue the filter chain
    }
}