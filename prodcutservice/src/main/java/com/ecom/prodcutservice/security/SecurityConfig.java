package com.ecom.prodcutservice.security;

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
//                    .csrf().disable()
                    .authorizeHttpRequests(authorize -> authorize
                                    .requestMatchers("/product/id","/category/price/id").hasRole("USER")// only users can read
                                    .requestMatchers("/product/create").hasRole("ADMIN")//only admin can create
                                    .requestMatchers("/category/create").hasRole("ADMIN")
                                    .requestMatchers(  "/category/","/category/id").permitAll()
                                    .requestMatchers("/product/**").permitAll()
//                                    .requestMatchers(  "/category/id").hasAuthority("USER")

                                    .anyRequest().authenticated()// prohabited all other functions
//                                .anyRequest().permitAll()// if incase we dont want to RISTRICT product service
                    );

        http.headers(headers -> headers.httpStrictTransportSecurity().disable())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

//                    .oauth2ResourceServer(oauth2 -> oauth2
//                            .jwt(jwt -> jwt
//                                    .jwtAuthenticationConverter(new CustomJwtAuthenticationConverter()))// implementing jwt
//                            );

            return http.build();
    }


@Bean
    public RestClient restClient(){
        return RestClient.builder()
                .baseUrl("http://localhost:8085/cart/").build();
}
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter(){
//            JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//            grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
//            grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
//
//            JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//            jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//            return jwtAuthenticationConverter;
//    }
//
}
