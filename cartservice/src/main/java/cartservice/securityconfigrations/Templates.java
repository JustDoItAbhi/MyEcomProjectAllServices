package cartservice.securityconfigrations;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Templates {

//
//    @Bean(name="productServiceRestClient")
//    @LoadBalanced
//    public RestClient productServiceRestClient(){
//        return RestClient.builder()
//                .baseUrl("http://localhost:8080")
//                .build();
//    }

    @Bean
//            (name="userServiceTemplte")
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}



