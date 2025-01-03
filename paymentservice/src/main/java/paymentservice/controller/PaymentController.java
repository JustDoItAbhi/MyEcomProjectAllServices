package paymentservice.controller;

import com.stripe.exception.StripeException;
import exceptions.OrderNotFetchedException;
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

    @GetMapping("/{id}")
    public ResponseEntity<CheckoutResponseDto> getOrderForPayment(@PathVariable("id")long id) throws StripeException, OrderNotFetchedException {
        return ResponseEntity.ok(paymentGateway.toPay(id));
    }

@GetMapping("/SUCCESS")
public ResponseEntity<String> paidSuccefully( )  {
    return ResponseEntity.ok("CONGRATULATIONS YOU MADE THE PAYMENT, YOUR ORDER IS READY TO DELIVER\n" +
            "PLEASE CLICK THE LINK AND ENTER YOUR EMAIL FOR ADDRESS\n" +
            "   http://localhost:8091/deliveryUser/youremail");
}
}
