package paymentservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;
import paymentservice.exceptions.OrderNotFetchedException;
import paymentservice.dtos.CheckoutResponseDto;


public interface PaymentGateway {
    CheckoutResponseDto toPay(long id,String email) throws StripeException, OrderNotFetchedException, JsonProcessingException;


}
