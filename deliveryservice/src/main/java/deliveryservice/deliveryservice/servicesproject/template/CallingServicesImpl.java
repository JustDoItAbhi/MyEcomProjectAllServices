package deliveryservice.deliveryservice.servicesproject.template;

import deliveryservice.deliveryservice.servicesproject.dto.CartResposneDtos;
import deliveryservice.deliveryservice.servicesproject.dto.UserResponseDto;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.UserNotExistsException;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
@Primary
public class CallingServicesImpl implements CallingServices{
    private RestTemplateBuilder restTemplateBuilder;

    public CallingServicesImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public UserResponseDto getUser(String userId) {
        RestTemplate restTemplate=restTemplateBuilder.build();
        Jwt jwt=(Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token= jwt.getTokenValue();

        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity=new HttpEntity<>(headers);
        String url="http://localhost:8090/user/getUserByid/"+userId;
        ResponseEntity<UserResponseDto>response=restTemplate.exchange(url,
                HttpMethod.GET,
                entity,
                UserResponseDto.class);
        if(response.getBody()==null){
            throw new UserNotExistsException("PLEASE SIGN UP "+userId);
        }
        return response.getBody();
    }

    @Override
    public CartResposneDtos fetchingFromCartServcie(long cartId){
        RestTemplate restTemplate=restTemplateBuilder.build();
        Jwt jwt=(Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwt.getTokenValue();
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity=new HttpEntity<>(headers);
        String url="http://localhost:8085/cart/getCartById/"+cartId;
        ResponseEntity<CartResposneDtos>response=restTemplate.exchange(url,HttpMethod.GET,entity, CartResposneDtos.class);
        if(response.getBody()==null){
            throw new RuntimeException("CART NOT FETCHIED ");
        }
        return response.getBody();
    }
}
