package cartservice.client;


import cartservice.securityconfigrations.expcetions.expectionsfiles.UserNotExistsException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CallingUserService {
private final RestTemplateBuilder restTemplateBuilder;
private final DiscoveryClient discoveryClient;

public CallingUserService(RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
    this.restTemplateBuilder = restTemplateBuilder;
    this.discoveryClient = discoveryClient;
}

    public UserResponseDto getUser(String email) {
    //storing token to header
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwt.getTokenValue();
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        // making call to user service with apigateway
        ServiceInstance serviceInstance=discoveryClient.getInstances("userservice").get(0);
        String url=serviceInstance.getUri()+"/user/getUserByid/"+email;
        ResponseEntity<UserResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, UserResponseDto.class);

        if(response.getBody()==null){
                throw new UserNotExistsException("PLEASE SIGN UP "+email);
        }
        return response.getBody();
      }
}
