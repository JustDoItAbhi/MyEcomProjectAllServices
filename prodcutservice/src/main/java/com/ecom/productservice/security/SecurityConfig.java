package com.ecom.productservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
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
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object>template=new RedisTemplate<>();
        template.setConnectionFactory(factory);
        GenericJackson2JsonRedisSerializer serializer=new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(serializer);
        return template;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests((authorize -> authorize
                            .requestMatchers("/product/create","/product/deleteSingleProduct/{id}","/product/UPDATE/{id}").hasRole("ADMIN")//only admin can create
                            .requestMatchers("/category/create","/category/update/","/category/price/id").hasRole("ADMIN")
                            .requestMatchers("/category/searchByCategoryName/{name}","/category/","/product/").permitAll()
                            .requestMatchers("/actuator/redis/**").permitAll()
                            .requestMatchers("/error").permitAll()
                            .requestMatchers("/category/getbyid").hasRole("ADMIN")
                            .requestMatchers("/category/","/category/price/{id}").permitAll()// OPEN TO USE
                            .requestMatchers("/product/get/{id}").permitAll()
                            .requestMatchers("/product/getProductByName/{name}").permitAll()
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
