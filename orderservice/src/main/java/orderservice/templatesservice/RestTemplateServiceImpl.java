package orderservice.templatesservice;


import orderservice.dtos.CartResposneDtos;
import orderservice.exceptions.CannotFetchDataFromUserService;
import orderservice.exceptions.SignUpException;
import orderservice.repositorties.UserDetailsReposirtoy;
import orderservice.users.userdtos.UserResponseDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service// MANAGED BY SPRING SERVICE BEAN ANNOTATION
public class RestTemplateServiceImpl implements RestTemplateService{//
    private final UserDetailsReposirtoy reposirtoy;// DECLARATION OF USER REPOSITORY
    private final RestTemplateBuilder restTemplateBuilder;// DECLARATION OF REST TEMPLATE
    private final DiscoveryClient discoveryClient;// DECLARATION OF EUREKA SERVER INSTANCE
// DEPENDENCY INJECTION
    public RestTemplateServiceImpl(UserDetailsReposirtoy reposirtoy, RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.reposirtoy = reposirtoy;
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }
    public CartResposneDtos fetchProduct(long cartId) {// FETCH PRODUCT FROM PRODUCT SERVICE
        RestTemplate restTemplate=restTemplateBuilder.build();

        ServiceInstance serviceInstance = discoveryClient.getInstances("cartservice").get(0);
        String serviceAUri = serviceInstance.getUri() + "/cart/getCartById/"+cartId;// API
        ResponseEntity<CartResposneDtos>response=restTemplate.getForEntity(serviceAUri, CartResposneDtos.class);
        if(response.getBody()==null){// PRODUCT VALIDATION
            throw new RuntimeException("CANNOT FETCH CART "+ cartId);
        }
        return response.getBody();
    }

    public UserResponseDto getUserById(String email)throws SignUpException {// FETCH USER BY USE EMAIL
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwt.getTokenValue();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);// ADD TOKEN TO HEADERS

        RestTemplate restTemplate = restTemplateBuilder.build();// START REST TEMPLATE BUILD
        List<ServiceInstance> instances = discoveryClient.getInstances("USERSERVICE");
        if (instances.isEmpty()) {// VALIDATION OF USER
            throw new RuntimeException("User service is not available");
        }
        ServiceInstance serviceInstance = instances.get(0);
        String url = serviceInstance.getUri().toString() + "/user/getUserByid/" + email;// API CALL

        ResponseEntity<UserResponseDto> template = restTemplate.exchange(url, HttpMethod.GET, entity, UserResponseDto.class);
        if (template.getBody() == null || template.getStatusCode() != HttpStatus.OK) {// VALIDATION AND SEND SIGN UP API IF USER IS NULL OR USER IS HAVING ERROR
            throw new CannotFetchDataFromUserService(("DEAR " + email + " SIGNUP http://localhost:8090/user/signup"));
        }
        return template.getBody();
    }


    public List<UserResponseDto> getAllUser(){// OPTIONAL GET ALL USERS
        RestTemplate restTemplate=restTemplateBuilder.build();
        Jwt jwt=(Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token =jwt.getTokenValue();
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?>entity=new HttpEntity<>(headers);
        ServiceInstance serviceInstance=discoveryClient.getInstances("userservice").get(0);
        String url=serviceInstance.getUri().toString()+"/user/";
        ResponseEntity<UserResponseDto[]> template=restTemplate.exchange(url,HttpMethod.GET,entity, UserResponseDto[].class);
        if (template.getBody() == null) {
            throw new CannotFetchDataFromUserService(("ERROR IN FETCHING USERSERVICE "));
        }
        return Arrays.asList(template.getBody());
    }
//    private String getJwtToken() {//JWT extraction logic
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof JwtAuthenticationToken) {
//            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
//            return jwtAuth.getToken().getTokenValue();
//        }
//        throw new IllegalStateException("JWT token is not available");
//    }
//public CartResposneDtos fetchProduct(long cartId) {// FETCH PRODUCT FROM PRODUCT SERVICE
//    RestTemplate restTemplate=restTemplateBuilder.build();
//    Jwt jwt=(Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    String token =jwt.getTokenValue();
//    HttpHeaders headers=new HttpHeaders();
//    headers.setBearerAuth(token);
//    HttpEntity<?> entity=new HttpEntity<>(headers);// ADDING TO HEADER
//    ServiceInstance serviceInstance = discoveryClient.getInstances("cartservice").get(0);
//    String serviceAUri = serviceInstance.getUri() + "/cart/getCartById/"+cartId;// API
//    ResponseEntity<CartResposneDtos>response=restTemplate.exchange(serviceAUri, HttpMethod.GET,entity, CartResposneDtos.class);
//    if(response.getBody()==null){// PRODUCT VALIDATION
//        throw new RuntimeException("CANNOT FETCH CART "+ cartId);
//    }
//    return response.getBody();
//}

}
