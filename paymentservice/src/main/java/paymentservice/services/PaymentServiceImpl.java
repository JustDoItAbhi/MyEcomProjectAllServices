package paymentservice.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import exceptions.PaymentConfirmed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import paymentservice.dtos.OrderResponseDto;
import paymentservice.dtos.OrderStatus;
import paymentservice.entity.OrderPayment;
import paymentservice.repository.OrderPaymentRepository;
import paymentservice.repository.PaymentRepository;
import paymentservice.services.Mapper;
import paymentservice.services.PaymentService;
import paymentservice.webhooks.EventMapper;
import paymentservice.webhooks.EventReciver;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderPaymentRepository orderPaymentRepository;
    private final RestTemplateBuilder restTemplateBuilder;
    private final DiscoveryClient discoveryClient;
    @Value("${stripe.secret.key}")
    private String stripeUniversalLink;

    @Value("${payment.redirect.url}")
    private String paymentRedirectUrl;
    String paymentlink="";
@Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderPaymentRepository orderPaymentRepository,
                              RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.paymentRepository = paymentRepository;
        this.orderPaymentRepository = orderPaymentRepository;
        this.restTemplateBuilder=restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }
    @Override
    public String toPay(OrderResponseDto dto) throws StripeException {
        Stripe.apiKey = stripeUniversalLink;
        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName("PRODUCT")
                .build();
        Product product = Product.create(productParams);
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setCurrency("USD")
                .setUnitAmount((long)dto.getPrice())
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
        ServiceInstance serviceInstance=discoveryClient.getInstances("orderservice").get(0);
       String url = serviceInstance.getUri().toString()+"/order/" + id;
        RestTemplate restTemplate=restTemplateBuilder.build();
        ResponseEntity<OrderResponseDto>response=restTemplate.getForEntity(url,OrderResponseDto.class);
        if (response.getBody() == null) {
            throw new RuntimeException("ORDER CANCELLED: " + id);
        }
//           payment = orderPaymentRepository.save(payment);
        String paymentUrl="";
           // Automatically call `toPay` if the order is successful
           if (OrderStatus.SUCESSFULL.equals(response.getBody().getOrderStatus())) {
               paymentUrl = toPay(response.getBody());
               System.out.println("PLEASE CLICK ON THIS LINK "+ paymentUrl);
           }
           paymentlink=paymentUrl;
        return paymentUrl;
    }

    @Override
    public EventReciver afterpaymentConfirmed(String link) {
        Event event=new Event();
    if(paymentlink==null){
        throw new RuntimeException("PLEASE PAY AGAIN");
    }
    return EventMapper.fromWebhookEven(event);
    }
}
