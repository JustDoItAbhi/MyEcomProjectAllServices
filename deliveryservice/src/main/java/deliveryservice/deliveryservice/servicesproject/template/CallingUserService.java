package deliveryservice.deliveryservice.servicesproject.template;

import deliveryservice.deliveryservice.servicesproject.dto.CartResposneDtos;
import deliveryservice.deliveryservice.servicesproject.dto.UserResponseDto;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
@Primary
public class CallingUserService implements  CallingServices{
private RestTemplateBuilder restTemplateBuilder;
private DiscoveryClient discoveryClient;


    public CallingUserService(RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }

    public UserResponseDto getUser(String userId){
        Jwt jwt=(Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token= jwt.getTokenValue();

        RestTemplate restTemplate=restTemplateBuilder.build();
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?>entity=new HttpEntity<>(headers);
        ServiceInstance serviceInstance=discoveryClient.getInstances("userservice").get(0);
        String url=serviceInstance.getUri()+"/user/getUserByid/"+userId;
        ResponseEntity<UserResponseDto>response=restTemplate.getForEntity(url, UserResponseDto.class);
        if(response.getBody()==null){
            throw new RuntimeException("USER NOT FETCHED "+userId);
        }
        return response.getBody();
    }

    @Override
    public CartResposneDtos fetchingFromCartServcie(long cartId) {
        return null;
    }
}
