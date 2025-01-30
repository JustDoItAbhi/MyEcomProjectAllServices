package com.ecom.productservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
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
                            .requestMatchers("/category/create","/category/update/","/category/price/id").hasRole("ADMIN")
                            .requestMatchers("/category/searchByCategoryName/{name}","/category/","/product/").permitAll()
                            .requestMatchers("/category/getbyid").hasRole("ADMIN")
                            .requestMatchers("/product/get/**","/category/").permitAll()// OPEN TO USE
                                    .anyRequest().authenticated()// REST ALL AUTHENTICATED
                    ))
                    // JWT ROLE BASE CHECK
                                    .oauth2ResourceServer(oauth2 -> oauth2
                                            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                                    );
         return http.build();
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {// JWT ROLE PROVIDER
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_"); // Ensure consistency
        authoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return authenticationConverter;
    }

@Bean
    public RestClient restClient(){// REST CLIENT BEAN
        return RestClient.builder()
                .baseUrl("http://localhost:8085/cart/").build();
}
}
