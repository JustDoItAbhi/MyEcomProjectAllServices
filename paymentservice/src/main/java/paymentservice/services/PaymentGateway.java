package paymentservice.services;

import com.stripe.exception.StripeException;
import exceptions.OrderNotFetchedException;
import paymentservice.dtos.CheckoutResponseDto;


public interface PaymentGateway {
    CheckoutResponseDto toPay(long id) throws StripeException, OrderNotFetchedException;
}
