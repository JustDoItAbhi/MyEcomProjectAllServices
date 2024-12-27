package orderservice.templates;


import orderservice.exceptions.CannotFetchDataFromUserService;
import orderservice.exceptions.SignUpException;
import orderservice.repositorties.UserDetailsReposirtoy;
import orderservice.users.userdtos.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserclientRestTemplate {
    private UserDetailsReposirtoy reposirtoy;
    private RestTemplateBuilder restTemplateBuilder;
    private DiscoveryClient discoveryClient;

    public UserclientRestTemplate(UserDetailsReposirtoy reposirtoy, RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.reposirtoy = reposirtoy;
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }

    public List<UserResponseDto> getAllUser(){
        RestTemplate restTemplate=restTemplateBuilder.build();
        ServiceInstance serviceInstance=discoveryClient.getInstances("userservice").get(0);
        String url=serviceInstance.getUri().toString()+"/user/";
//        String url="http://localhost:8090/user/";
        ResponseEntity<UserResponseDto[]> template=restTemplate.getForEntity(url,UserResponseDto[].class);
        if (template.getBody() == null) {
            throw new CannotFetchDataFromUserService(("ERROR IN FETCHING USERSERVICE "));
        }
        return Arrays.asList(template.getBody());
    }
    public UserResponseDto getUserById(String email){
        RestTemplate restTemplate = restTemplateBuilder.build();
        List<String> services = discoveryClient.getServices();
        System.out.println("Registered services: " + services);

        List<ServiceInstance> instances = discoveryClient.getInstances("USERSERVICE");
        if (instances.isEmpty()) {
            throw new RuntimeException("User service is not available");
        }
        ServiceInstance serviceInstance = instances.get(0);
        String url = serviceInstance.getUri().toString() + "/user/getUserByid/" + email;

        ResponseEntity<UserResponseDto> response = restTemplate.getForEntity(url, UserResponseDto.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("PLEASE SIGN UP " + email);
        }

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