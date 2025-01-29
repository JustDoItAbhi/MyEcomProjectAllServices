package cartservice.client;

import cartservice.dtos.ProductResponseDto;
import cartservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ProductServiceClient {
    private final ProductRepository productRepository;
  private final RestTemplateBuilder restTemplateBuilder;
    private final DiscoveryClient discoveryClient;

    public ProductServiceClient(ProductRepository productRepository,
                                RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.productRepository = productRepository;
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }

    public ProductResponseDto fetchProductById(long id) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        ServiceInstance serviceInstance = discoveryClient.getInstances("productservice").get(0);
        String serviceAUri = serviceInstance.getUri() + "/product/get/"+id;
//        String serviceAUri="http://localhost:8089/product/get/"+id;
        ResponseEntity<ProductResponseDto> response=restTemplate.getForEntity(serviceAUri,ProductResponseDto.class);

        return response.getBody();
    }

}
