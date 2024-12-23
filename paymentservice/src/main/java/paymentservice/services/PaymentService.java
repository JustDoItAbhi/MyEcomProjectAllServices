package paymentservice.services;

import com.stripe.exception.StripeException;
import paymentservice.entity.OrderPayment;

public interface PaymentService {
    String toPay(OrderPayment orderPayment) throws StripeException;
    String createPaymentEntity(long id) throws StripeException;

}
