package cartservice.configrations;

import cartservice.userdtos.UserResponseDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebClient {
//
//    @Bean(name = "userServiceRestClient")
//    public RestClient userServiceRestClient(){
//        return RestClient.builder()
//                .baseUrl("http://localhost:8090/user")
//                .build();
//    }

    @Bean(name="productServiceRestClient")
    public RestClient productServiceRestClient(){
        return RestClient.builder()
                .baseUrl("http://localhost:8080/product")
                .build();
    }

    @Bean
//            (name="userServiceTemplte")
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}



