package cartservice.client;

import cartservice.dtos.ProductResponseDto;
import cartservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import java.util.List;

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

    public List<ProductResponseDto> fetchProduct() {
        RestTemplate restTemplate=restTemplateBuilder.build();
        ServiceInstance serviceInstance = discoveryClient.getInstances("productservice").get(0);
        String serviceAUri = serviceInstance.getUri().toString() + "/product/";
//        String url="http://PRODUCTSERVICE/product/";
        ResponseEntity<ProductResponseDto[]> response=restTemplate.getForEntity(serviceAUri,ProductResponseDto[].class);
        if(response.getBody()==null){
        throw new RuntimeException("entity not found");
}
        return List.of(response.getBody());
    }



    public ProductResponseDto fetchProductById(long id) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        ServiceInstance serviceInstance = discoveryClient.getInstances("productservice").get(0);
        String serviceAUri = serviceInstance.getUri().toString() + "/product/get/"+id;
//        String url="http://PRODUCTSERVICE/product/get/"+id;
        ResponseEntity<ProductResponseDto> response=restTemplate.getForEntity(serviceAUri,ProductResponseDto.class);

        return response.getBody();
    }





    private String getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
            return jwtAuth.getToken().getTokenValue();
        }
        throw new IllegalStateException("JWT token is not available");
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
        System.out.println("ENTERED BY CART SERVICE");
    }

}
