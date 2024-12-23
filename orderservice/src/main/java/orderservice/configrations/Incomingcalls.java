package orderservice.configrations;

import orderservice.dtos.CartResposneDtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Incomingcalls implements WebMvcConfigurer {
    private RestTemplateBuilder restTemplateBuilder;

    public Incomingcalls(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public CartResposneDtos fetchProduct(String userId) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        String url="http://localhost:8085/cart/getCartById/"+userId;
        ResponseEntity<CartResposneDtos>response=restTemplate.getForEntity(url, CartResposneDtos.class);
        if(response.getBody()==null){
            throw new RuntimeException("CANNOT FETCH CART "+ userId);
        }
        return response.getBody();
    }

    private String getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
            String token= jwtAuth.getToken().getTokenValue();
            System.out.println("JWT Token: " + token);
            return token;
        }
        throw new IllegalStateException("JWT token is not available");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
        System.out.println("ENTERED BY CART SERVICE");
    }
    public String getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            System.out.println("HERE IS ROLLLLLLLLLLEEEEEEE");
            return jwt.getClaimAsStringList("roles").toString(); // Extract "roles" claim
        }
        return "No roles available";
    }
}

