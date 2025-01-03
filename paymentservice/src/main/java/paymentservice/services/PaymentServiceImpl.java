package paymentservice.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import exceptions.OrderNotFetchedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import paymentservice.dtos.OrderResponseDto;
import paymentservice.dtos.CheckoutResponseDto;
import clinets.OrderServiceClient;

@Component
public class PaymentServiceImpl implements PaymentGateway {

    @Value("${stripe.secret.key}")
    private String stripeUniversalLink;

    @Value("${payment.redirect.url}")
    private String paymentRedirectUrl;
    private final OrderServiceClient orderServiceClient;


    public PaymentServiceImpl(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }



    @Override
    public CheckoutResponseDto toPay(long id) throws StripeException, OrderNotFetchedException {
        Stripe.apiKey = stripeUniversalLink;

        OrderResponseDto dto = orderServiceClient.getOrderDetails(id);


        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName("PRODUCT")
                .build();
        Product product = Product.create(productParams);
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setCurrency("USD")
                .setUnitAmount((long) dto.getPrice())
                .setProduct(product.getId()) // Link to the created product
                .build();
        Price price = Price.create(priceParams);
        // Create a payment link in Stripe
        PaymentLinkCreateParams linkParams = PaymentLinkCreateParams.builder()
                .addLineItem(PaymentLinkCreateParams.LineItem.builder()
                        .setPrice(price.getId())
                        .setQuantity(1L)
                        .build())
                .setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                        .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                .setUrl(paymentRedirectUrl)
                                .build())
                        .build())

                .putMetadata("order_id", String.valueOf(dto.getOrderid())) // Add metadata

                .build();


        PaymentLink paymentLink = PaymentLink.create(linkParams);

        return new CheckoutResponseDto.Builder()
                .setUrl(paymentLink.getUrl())
                .setMessage("HERE IS YOUR LINK TO PAY")
                .setStatus("SUCCESSFUL")
                .setLineItems(linkParams.getLineItems())
                .build();
    }

}
