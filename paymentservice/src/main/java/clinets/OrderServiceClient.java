package clinets;

import exceptions.OrderNotFetchedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import paymentservice.dtos.OrderResponseDto;

import java.util.List;
@Component
public class OrderServiceClient {
    private final RestTemplateBuilder restTemplateBuilder;
    private final DiscoveryClient discoveryClient;

    public OrderServiceClient(RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }

    public OrderResponseDto getOrderDetails(long id) throws OrderNotFetchedException {
        RestTemplate restTemplate=restTemplateBuilder.build();
        List<ServiceInstance> instances = discoveryClient.getInstances("orderservice");

        if (instances.isEmpty()) {
            throw new RuntimeException("No instances of 'orderservice' found in service registry.");
        }
        // Pick the first available instance (improve with load balancing as needed)
        ServiceInstance serviceInstance = instances.get(0);
        String url = serviceInstance.getUri() + "/order/" + id;

        ResponseEntity<OrderResponseDto> response = restTemplate.getForEntity(url, OrderResponseDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            System.out.println("DATA FETCHED " + id);
            return response.getBody();
        } else {
            throw new OrderNotFetchedException("Failed to fetch order details for ID: " + id);
        }
    }

}