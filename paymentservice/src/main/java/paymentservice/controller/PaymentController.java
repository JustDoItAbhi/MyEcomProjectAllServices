package paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import paymentservice.exceptions.OrderNotFetchedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paymentservice.dtos.CheckoutResponseDto;
import paymentservice.exceptions.UserNotFoundException;
import paymentservice.services.PaymentGateway;

import java.util.Collection;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/pay")
public class PaymentController {

    private final PaymentGateway paymentGateway;

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com)$";//EMAIL VALIDATION CHECK
   //DEPENDENCY
    public PaymentController(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    @GetMapping("/{id}/{email}")// GET PAYMENT BY EMAIL
    public ResponseEntity<CheckoutResponseDto> getOrderForPayment(@PathVariable("id") long id,@PathVariable ("email")String email) throws StripeException, OrderNotFetchedException, JsonProcessingException {
        if(!email.matches(EMAIL_PATTERN)){
            throw new UserNotFoundException("Invalid email! Only Gmail and Yahoo emails are allowed."+email);
        }
        return ResponseEntity.ok(paymentGateway.toPay(id,email));
    }
    @GetMapping("/debug") // admin// CHECK ROLE BY TOKEN
    public ResponseEntity<String> debugAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authenticated");
        }
        // Retrieve roles from authentication
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        return ResponseEntity.ok("User roles: " + roles);
    }

}
