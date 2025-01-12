package paymentservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import paymentservice.customiseloginforemail.KafkaProducerClinet;
import paymentservice.customiseloginforemail.SendEmailDto;
import paymentservice.exceptions.OrderNotFetchedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import paymentservice.dtos.OrderResponseDto;
import paymentservice.dtos.CheckoutResponseDto;
import paymentservice.clinets.OrderServiceClient;
import paymentservice.exceptions.UserNotFoundException;

@Component
public class PaymentServiceImpl implements PaymentGateway {

    @Value("${stripe.secret.key}")
    private String stripeUniversalLink;

    @Value("${payment.redirect.url}")
    private String paymentRedirectUrl;
    private final OrderServiceClient orderServiceClient;
    private final KafkaProducerClinet kafkaProducerClinet;
    private final ObjectMapper objectMapper;

    public PaymentServiceImpl(OrderServiceClient orderServiceClient, KafkaProducerClinet kafkaProducerClinet, ObjectMapper objectMapper) {
        this.orderServiceClient = orderServiceClient;
        this.kafkaProducerClinet = kafkaProducerClinet;
        this.objectMapper = objectMapper;
    }

    @Override
    public CheckoutResponseDto toPay(long id,String email) throws StripeException, OrderNotFetchedException, JsonProcessingException {
        if(email==null){
            throw new UserNotFoundException("PLEASE ENTER EMAIL "+email);
        }
        Stripe.apiKey = stripeUniversalLink;

        OrderResponseDto dto = orderServiceClient.getOrderDetails(id);

        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName("PRODUCT")
                .build();
        Product product = Product.create(productParams);
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setCurrency("USD")
                .setUnitAmount((long) dto.getPrice()*100)
                .setProduct(product.getId()) // Link to the created product
                .build();
        Price price = Price.create(priceParams);
        // Create a payment link in Stripe
        PaymentLinkCreateParams linkParams = PaymentLinkCreateParams.builder()
                .addLineItem(PaymentLinkCreateParams.LineItem.builder()
                        .setPrice(price.getId())
                        .setQuantity(1L) // Adjust quantity dynamically if needed
                        .setAdjustableQuantity(PaymentLinkCreateParams.LineItem.AdjustableQuantity.builder()
                                .setEnabled(true) // Allow quantity adjustments
                                .setMinimum(1L) // Minimum quantity
                                .setMaximum(10L) // Maximum quantity
                                .build())
                        .build())
                .setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                        .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                .setUrl(paymentRedirectUrl+"/"+dto.getCartId()+"/"+email)
                                .build())
                        .build())

                .build();


        PaymentLink paymentLink = PaymentLink.create(linkParams);
        SendEmailDto emailDto=new SendEmailDto();
        emailDto.setTo(email);
        emailDto.setFrom("Pattorney0@gmail.com");
        emailDto.setSubject("PLEASE PAY FOR YOUR ORDER");
        emailDto.setBody("here is link to pay "+ paymentLink.getUrl()+" currency "+paymentLink.getCurrency());

        kafkaProducerClinet.sendMessage("sendemail",
                objectMapper.writeValueAsString(emailDto));

                return new CheckoutResponseDto.Builder()
                .setUrl(paymentLink.getUrl())
                .setMessage("HERE IS YOUR LINK TO PAY")
                .setStatus("SUCCESSFUL")
                .setLineItems(linkParams.getLineItems())
                .setCartId(dto.getCartId())
                .setPrice(dto.getPrice())
                .setOrderId(id)
                .build();
    }


    }


