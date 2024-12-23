package paymentservice.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import exceptions.PaymentConfirmed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import paymentservice.dtos.OrderResponseDto;
import paymentservice.dtos.OrderStatus;
import paymentservice.entity.OrderPayment;
import paymentservice.repository.OrderPaymentRepository;
import paymentservice.repository.PaymentRepository;
import paymentservice.services.Mapper;
import paymentservice.services.PaymentService;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderPaymentRepository orderPaymentRepository;
    private final RestClient restClient;

    @Value("${stripe.secret.key}")
    private String stripeUniversalLink;

    @Value("${payment.redirect.url}")
    private String paymentRedirectUrl;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderPaymentRepository orderPaymentRepository,
                              RestClient restClient) {
        this.paymentRepository = paymentRepository;
        this.orderPaymentRepository = orderPaymentRepository;
        this.restClient = restClient;
    }

    @Override
    public String toPay(OrderPayment orderPayment) throws StripeException {
        Stripe.apiKey = stripeUniversalLink;
        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName("PRODUCT")
                .build();
        Product product = Product.create(productParams);
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setCurrency("USD")
                .setUnitAmount((long) orderPayment.getPrice())
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
                                .setUrl(paymentRedirectUrl) // Use value from properties
                                .build())
                        .build())
                .build();

        PaymentLink paymentLink = PaymentLink.create(linkParams);

        return paymentLink.getUrl();
    }

    @Override
    public String createPaymentEntity(long id) throws StripeException {
        // Fetch order details
        OrderResponseDto responseDto = restClient.get()
                .uri("/" + id)
                .retrieve()
                .body(OrderResponseDto.class);

        if (responseDto == null) {
            throw new RuntimeException("ORDER CANCELLED: " + id);
        }

        // Map response to entity and save
        OrderPayment payment = Mapper.fromDto(responseDto);
//           payment = orderPaymentRepository.save(payment);
        String paymentUrl="";
           // Automatically call `toPay` if the order is successful
           if (OrderStatus.SUCESSFULL.equals(payment.getOrderStatus())) {
               paymentUrl = toPay(payment);
               System.out.println("PLEASE CLICK ON THIS LINK "+ paymentUrl);

           }

        return paymentUrl;
    }
}
