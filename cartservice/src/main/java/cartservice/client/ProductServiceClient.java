package cartservice.client;

import cartservice.dtos.ProductResponseDto;
import cartservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
    @Autowired
    private ProductRepository productRepository;
    @Autowired
  private RestTemplateBuilder restTemplateBuilder;


    public List<ProductResponseDto> fetchProduct() {
        RestTemplate restTemplate=restTemplateBuilder.build();
        String url="http://localhost:8080/product/";
ResponseEntity<ProductResponseDto[]> response=restTemplate.getForEntity(url,ProductResponseDto[].class);

        return List.of(response.getBody());
    }



    public ProductResponseDto fetchProductbYiD(long id) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        String url="http://localhost:8080/product/get/"+id;
        ResponseEntity<ProductResponseDto> response=restTemplate.getForEntity(url,ProductResponseDto.class);

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
