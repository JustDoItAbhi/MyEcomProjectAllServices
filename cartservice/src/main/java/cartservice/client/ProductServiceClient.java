package cartservice.client;

import cartservice.dtos.ProductResponseDto;
import cartservice.entity.CartItems;
import cartservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Service
public class ProductServiceClient {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    @Qualifier("productServiceRestClient")
    private RestClient restClient; // restclient used to call product service

    public ProductResponseDto fetchProduct(long productId, String jwtToken) {
        ProductResponseDto responseDto=new ProductResponseDto();
        responseDto=  restClient.get()
                .uri("http://localhost:8080/product/" + productId)// address to product service
                .headers(headers -> headers.setBearerAuth(jwtToken)) // Set Bearer Token // storing token in header
                .retrieve()
                .body(ProductResponseDto.class);
        return responseDto;
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
