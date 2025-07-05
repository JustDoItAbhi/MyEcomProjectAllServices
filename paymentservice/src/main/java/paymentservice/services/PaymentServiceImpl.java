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
public class PaymentServiceImpl implements PaymentGateway {// PAYMENT SERVICE IMPLEMENTATION LAYER

    @Value("${stripe.secret.key}")
    private String stripeUniversalLink;

    @Value("${payment.redirect.url}")
    private String paymentRedirectUrl;
    private final OrderServiceClient orderServiceClient;
    private final KafkaProducerClinet kafkaProducerClinet;
    private final ObjectMapper objectMapper;
    //CONSTRUCTOR
    public PaymentServiceImpl(OrderServiceClient orderServiceClient, KafkaProducerClinet kafkaProducerClinet, ObjectMapper objectMapper) {
        this.orderServiceClient = orderServiceClient;
        this.kafkaProducerClinet = kafkaProducerClinet;
        this.objectMapper = objectMapper;
    }

    @Override
    public CheckoutResponseDto toPay(long id,String email) throws StripeException, OrderNotFetchedException, JsonProcessingException {
        if(email==null){//CHECK NULL EMAIL
            throw new UserNotFoundException("PLEASE ENTER EMAIL "+email);
        }
        Stripe.apiKey = stripeUniversalLink;// STRIP API KEY

        OrderResponseDto dto = orderServiceClient.getOrderDetails(id);

        ProductCreateParams productParams = ProductCreateParams.builder()// PRODUCT PARAM
                .setName("PRODUCT")
                .build();
        Product product = Product.create(productParams);
        PriceCreateParams priceParams = PriceCreateParams.builder()//PRICE PARAM
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
                                .setUrl(paymentRedirectUrl+email)//REDIRECT URL
                                .build())
                        .build())

                .build();


        PaymentLink paymentLink = PaymentLink.create(linkParams);// PAYMENT LINK
        SendEmailDto emailDto=new SendEmailDto();
        emailDto.setTo(email);
        emailDto.setFrom("Pattorney0@gmail.com");//EMAIL SENDER
        emailDto.setSubject("PLEASE PAY FOR YOUR ORDER");// SUBJECT MESSAGE
        emailDto.setBody("here is link to pay "+ paymentLink.getUrl()+" currency "+paymentLink.getCurrency());//PAYMENT LINK AND CURRENCY

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


