package paymentservice.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paymentservice.entity.OrderPayment;
import paymentservice.services.PaymentService;

@RestController
@RequestMapping("/pay")
public class PaymentController {
private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
//    @GetMapping("/getLink/{userId}")
//public ResponseEntity<String> getPaymentDetails(@PathVariable("userId")long userId) throws StripeException {
//        return ResponseEntity.ok(paymentService.toPay(userId));
//    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderForPayment(@PathVariable("id")long id) throws StripeException {
        return ResponseEntity.ok(paymentService.createPaymentEntity(id));
    }

}
