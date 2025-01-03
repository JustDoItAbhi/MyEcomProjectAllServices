package paymentservice.controller;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import delivery.DeliveryServiceNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import paymentservice.dtos.OrderStatus;
import paymentservice.entity.OrderPayment;
import paymentservice.entity.Payments;
import paymentservice.repository.OrderPaymentRepository;
import paymentservice.repository.PaymentRepository;

@RestController
@RequestMapping("/webhooks")
public class StripeWebhookController {

    @Value("${stripe.secret.key}")
    private String endpointSecret; // Set this value from your Stripe Dashboard
    @Value("${stripe.webhook.secret}")
    private String stripeWebhookSecret;

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderPaymentRepository orderPaymentRepository;


    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;
        try {
            // Verify webhook signature
            event = Webhook.constructEvent(
                    payload, sigHeader, stripeWebhookSecret
            );
        } catch (SignatureVerificationException e) {
            // If the signature doesn't match, return an error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        // Handle the event
        switch (event.getType()) {
            case "payment_intent.succeeded":
                handlePaymentIntentSucceeded(event);
                break;
            case "payment_intent.payment_failed":
                handlePaymentIntentFailed(event);
                break;
            case "checkout.session.completed":
                handleCheckoutSessionCompleted(event);
                break;
            // Add other event types as needed
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok("Event handled");
    }

    private void handlePaymentIntentSucceeded(Event event) {
        // Extract payment details
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject().orElseThrow();
        System.out.println("Payment for " + paymentIntent.getAmount() + " succeeded.");
        System.out.println("Payment for " + paymentIntent.getId() + " customer id.");
        System.out.println("Payment for " + paymentIntent.getCurrency() + " currency.");
        System.out.println("Payment for " + paymentIntent.getStatus() + " status ");
        // Update your payment and order entities (example):
        OrderPayment orderPayment=new OrderPayment();
        if(paymentIntent.getStatus().equals("payment_intent.succeeded")) {
            orderPayment.setOrderStatus(OrderStatus.SUCESSFULL);
        }
        orderPayment.setUserId(paymentIntent.getId());
        orderPayment.setPrice(paymentIntent.getAmount());
        orderPaymentRepository.save(orderPayment);
        // paymentRepository.updateStatus(paymentIntent.getId(), "SUCCESS");

        // Create notification for the delivery service
        DeliveryServiceNotification notification = new DeliveryServiceNotification();
        notification.setOrderId(paymentIntent.getId());
        notification.setPaymentStatus("SUCCESS");
        notification.setAmount(paymentIntent.getAmount() / 100.0); // Convert to dollars (Stripe uses cents)

        // Send notification to the delivery service
        sendNotificationToDeliveryService(notification);
    }
    private void sendNotificationToDeliveryService(DeliveryServiceNotification notification) {
        RestTemplate restTemplate = new RestTemplate();
        String deliveryServiceUrl = "http://localhost:8087/pay/notifyPaymentDetails"; // Replace with actual URL
        try {
            // Send the notification to the delivery service

            restTemplate.postForObject(deliveryServiceUrl, notification, String.class);
        } catch (Exception e) {
            System.out.println("Failed to send notification to delivery service: " + e.getMessage());
        }
    }

    private void handlePaymentIntentFailed(Event event) {// handling fail payment
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject().orElseThrow();
        System.out.println("Payment failed: " + paymentIntent.getLastPaymentError().getMessage());
    }

    private void handleCheckoutSessionCompleted(Event event) {  // handle if payment successfull
        Session session = (Session) event.getDataObjectDeserializer()
                .getObject().orElseThrow();
        System.out.println("Checkout session completed for session ID: " + session.getId());
    }
}