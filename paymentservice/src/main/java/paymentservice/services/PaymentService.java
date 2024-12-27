package paymentservice.services;

import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import paymentservice.dtos.OrderResponseDto;
import paymentservice.webhooks.EventReciver;

public interface PaymentService {
    String toPay(OrderResponseDto dto) throws StripeException;
    String createPaymentEntity(long id) throws StripeException;
    EventReciver afterpaymentConfirmed(String link);

}
