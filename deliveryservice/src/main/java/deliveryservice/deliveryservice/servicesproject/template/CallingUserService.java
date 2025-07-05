package deliveryservice.deliveryservice.servicesproject.template;

import deliveryservice.deliveryservice.servicesproject.dtos.CartResposneDtos;
import deliveryservice.deliveryservice.servicesproject.dtos.UserResponseDto;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.UserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
@Primary // CHOOSE SERVICE WITH PRIORITY
public class CallingUserService implements  CallingServices{// STRATRGY AND LAYER PATTERN FOR USER SERVCIE IMPLEMENTATION
private final RestTemplateBuilder restTemplateBuilder;// DECLARTION OF REST TEMPLATE
private final DiscoveryClient discoveryClient;// DECLARATION OF EURECA DISCOVERY CLINET INSTANCE
//    @Autowired
//    private RedisTemplate<String,Object> redisTemplate;

    // CONSTRUCTOR
    public CallingUserService(RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }

    public UserResponseDto getUser(String emailId){// GET USER
//        UserResponseDto userResponseDto= (UserResponseDto) redisTemplate.opsForValue().get("userResponse");
//        if(userResponseDto!=null){
//            return userResponseDto;
//        }
        Jwt jwt=(Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token= jwt.getTokenValue();

        RestTemplate restTemplate=restTemplateBuilder.build();
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?>entity=new HttpEntity<>(headers);// ADD TOKEN TO HEADER

        ServiceInstance serviceInstance=discoveryClient.getInstances("userservice").get(0);
        String url=serviceInstance.getUri()+"/user/getUserByid/"+emailId;// USER API
        ResponseEntity<UserResponseDto>response=restTemplate.exchange(url,HttpMethod.GET,entity, UserResponseDto.class);
        if(response.getBody()==null){// USER VALIDATION
            throw new UserNotExistsException("USER NOT FETCHED "+emailId);
        }
//        redisTemplate.opsForValue().set("userResponse",userResponseDto, Duration.ofSeconds(5));
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
