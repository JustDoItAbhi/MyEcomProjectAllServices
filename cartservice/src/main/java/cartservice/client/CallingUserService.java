package cartservice.client;


import cartservice.expcetions.expectionsfiles.UserNotExistsException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CallingUserService {
private RestTemplateBuilder restTemplateBuilder;
private DiscoveryClient discoveryClient;

    public CallingUserService(RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }

//    public UserResponseDto getUser(String userId){
//        RestTemplate restTemplate=restTemplateBuilder.build();
//        ServiceInstance serviceInstance=discoveryClient.getInstances("userservice").get(0);
//        String url=serviceInstance.getUri()+"/user/getUserByid/"+userId;
//        ResponseEntity<UserResponseDto>response=restTemplate.getForEntity(url, UserResponseDto.class);
//        if(response.getBody()==null){
//            throw new UserNotExistsException("USER NOT FETCHED "+userId);
//        }
//        return response.getBody();
//    }
    public UserResponseDto getUser(String email){
        RestTemplate restTemplate=restTemplateBuilder.build();
//        ServiceInstance serviceInstance=discoveryClient.getInstances("userservice").get(0);
//        String url=serviceInstance.getUri()+"/user/getUserByid/"+userId;
        String url="http://localhost:8090/user/getUserByid/"+email;
        ResponseEntity<UserResponseDto>response=restTemplate.getForEntity(url, UserResponseDto.class);
        if(response.getBody()==null){
            throw new UserNotExistsException("USER NOT FETCHED "+email);
        }
        return response.getBody();
    }
}
