package deliveryservice.deliveryservice.servicesproject.template;

import deliveryservice.deliveryservice.servicesproject.dto.CartResposneDtos;
import deliveryservice.deliveryservice.servicesproject.dto.UserResponseDto;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.UserNotExistsException;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
@Primary // CHOOSE SERVICE WITH PRIORITY
public class CallingUserService implements  CallingServices{// STRATRGY AND LAYER PATTERN FOR USER SERVCIE IMPLEMENTATION
private final RestTemplateBuilder restTemplateBuilder;// DECLARTION OF REST TEMPLATE
private final DiscoveryClient discoveryClient;// DECLARATION OF EURECA DISCOVERY CLINET INSTANCE

    // CONSTRUCTOR
    public CallingUserService(RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }

    public UserResponseDto getUser(String userId){// GET USER
        Jwt jwt=(Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token= jwt.getTokenValue();

        RestTemplate restTemplate=restTemplateBuilder.build();
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?>entity=new HttpEntity<>(headers);// ADD TOKEN TO HEADER

        ServiceInstance serviceInstance=discoveryClient.getInstances("userservice").get(0);
        String url=serviceInstance.getUri()+"/user/getUserByid/"+userId;// USER API
        ResponseEntity<UserResponseDto>response=restTemplate.exchange(url,HttpMethod.GET,entity, UserResponseDto.class);
        if(response.getBody()==null){// USER VALIDATION
            throw new UserNotExistsException("USER NOT FETCHED "+userId);
        }
        return response.getBody();
    }

    @Override
    public CartResposneDtos fetchingFromCartServcie(long cartId) {//GET  PRODUCT BY ID
        RestTemplate restTemplate=restTemplateBuilder.build();
        Jwt jwt=(Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwt.getTokenValue();
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity=new HttpEntity<>(headers);// ADD TOKEN TO HEADER
        String url="http://localhost:8085/cart/getCartById/"+cartId;// API
        ResponseEntity<CartResposneDtos>response=restTemplate.exchange(url, HttpMethod.GET,entity, CartResposneDtos.class);
        if(response.getBody()==null){// VALIDATION
            throw new RuntimeException("CART NOT FETCHIED ");
        }
        return response.getBody();
    }
}
