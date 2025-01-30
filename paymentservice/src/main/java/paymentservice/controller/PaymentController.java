package paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;
import paymentservice.exceptions.OrderNotFetchedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paymentservice.dtos.CheckoutResponseDto;
import paymentservice.exceptions.UserNotFoundException;
import paymentservice.services.PaymentGateway;


@RestController
@RequestMapping("/pay")
public class PaymentController {

    private final PaymentGateway paymentGateway;

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com)$";
    public PaymentController(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }


    @GetMapping("/{id}/{email}")
    public ResponseEntity<CheckoutResponseDto> getOrderForPayment(@PathVariable("id") long id,@PathVariable ("email")String email) throws StripeException, OrderNotFetchedException, JsonProcessingException {
        if(!email.matches(EMAIL_PATTERN)){
            throw new UserNotFoundException("Invalid email! Only Gmail and Yahoo emails are allowed."+email);
        }
        return ResponseEntity.ok(paymentGateway.toPay(id,email));
    }

}
