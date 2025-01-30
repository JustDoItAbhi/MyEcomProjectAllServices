package orderservice.configrations;

import orderservice.dtos.CartResposneDtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
public class Incomingcalls  {
    private final RestTemplateBuilder restTemplateBuilder;
    private final DiscoveryClient discoveryClient;

    public Incomingcalls(RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }

    public CartResposneDtos fetchProduct(long cartId) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        Jwt jwt=(Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token =jwt.getTokenValue();
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity=new HttpEntity<>(headers);
        ServiceInstance serviceInstance = discoveryClient.getInstances("cartservice").get(0);
        String serviceAUri = serviceInstance.getUri().toString() + "/cart/getCartById/"+cartId;
        ResponseEntity<CartResposneDtos>response=restTemplate.exchange(serviceAUri, HttpMethod.GET,entity, CartResposneDtos.class);
        if(response.getBody()==null){
            throw new RuntimeException("CANNOT FETCH CART "+ cartId);
        }
        return response.getBody();
    }

}

