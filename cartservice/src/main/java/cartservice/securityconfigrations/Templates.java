package cartservice.securityconfigrations;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Templates {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {// REST TEMPLATE BEAN
        return new RestTemplate();
    }

}



