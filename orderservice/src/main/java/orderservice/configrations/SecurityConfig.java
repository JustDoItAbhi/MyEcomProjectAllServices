package orderservice.configrations;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {// SPRING SECURITY CONFIGRATIONS
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                    .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(  "/order/**").hasRole("ADMIN")
//                        .requestMatchers("/order/").permitAll()
//                        .requestMatchers("/pay/").permitAll()
                                .requestMatchers("/order/getCartById/{id}").permitAll()
                                .anyRequest().authenticated()// prohabited all other functions
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );


        return http.build();
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {// REST TEMPLATE BEAN WITH API GATWAY LOAD BALANCER
        return restTemplateBuilder.build();
    }
    @Bean// SPRING SECURITY BEAN ROLE CUSTOMISATION
    public JwtAuthenticationConverter jwtAuthenticationConverter() {//ROLE CREATATION FACTORY DESIGN PETTERN
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_");
        authoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return authenticationConverter;
    }
}
