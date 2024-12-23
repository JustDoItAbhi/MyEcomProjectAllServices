package paymentservice.configrations;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import paymentservice.dtos.OrderResponseDto;
@Component
public class CallOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallOrderService.class);

    private final RestClient restClient;

    public CallOrderService(RestClient restClient) {
        this.restClient = restClient;
    }


//    public OrderResponseDto fetchProduct(long id) {
//        try {
//            return restClient.get()
//                    .uri("/", id) // Use path parameters
//                    .retrieve()
//                    .body(OrderResponseDto.class);
//            // Synchronously fetch the response
//        } catch (Exception e) {
//            throw new RuntimeException("Error fetching order details for ID: " + id, e);
//        }

//    }
}


//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("*");
//        System.out.println("ENTERED BY CART SERVICE");
//    }
//}
