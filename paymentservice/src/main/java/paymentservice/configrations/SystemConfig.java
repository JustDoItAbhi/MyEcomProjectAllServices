package paymentservice.configrations;

import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import paymentservice.dtos.OrderResponseDto;

@Configuration
public class SystemConfig {// SYSTEM CONFIGRATION
    @Bean// SECURITY FILTER CHAIN
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/order/{id}").hasRole("ADMIN")// ONLY ADMIN USE
                        .requestMatchers("/order/","/pay/").permitAll()// PUBLIC ACCESS
                        .anyRequest().authenticated()// prohabited all other functions
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );

        return http.build();
    }
    @Bean// SPRING SECURITY FACTORY PATTERN FOR ROLE
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_"); // Ensure consistency
        authoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return authenticationConverter;
    }
    @Bean// REST TEMLATE BEAN
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
