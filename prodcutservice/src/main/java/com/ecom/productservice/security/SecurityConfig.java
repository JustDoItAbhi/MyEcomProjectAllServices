package com.ecom.productservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests((authorize -> authorize
                            .requestMatchers("/product/create").hasRole("ADMIN")//only admin can create
                            .requestMatchers("/category/create").hasRole("ADMIN")
                                    .anyRequest().permitAll()
                    )
);
        http.headers(headers -> headers.httpStrictTransportSecurity().disable())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults())
                );
         return http.build();
    }

@Bean
    public RestClient restClient(){
        return RestClient.builder()
                .baseUrl("http://localhost:8085/cart/").build();
}
}
