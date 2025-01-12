package paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;
import paymentservice.exceptions.OrderNotFetchedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paymentservice.dtos.CheckoutResponseDto;
import paymentservice.services.PaymentGateway;


@RestController
@RequestMapping("/pay")
public class PaymentController {

    private final PaymentGateway paymentGateway;


    public PaymentController(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }


    @GetMapping("/{id}/{email}")
    public ResponseEntity<CheckoutResponseDto> getOrderForPayment(@PathVariable("id") long id,@PathVariable ("email")String email) throws StripeException, OrderNotFetchedException, JsonProcessingException {
        return ResponseEntity.ok(paymentGateway.toPay(id,email));
    }

}
