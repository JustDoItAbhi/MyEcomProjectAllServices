package com.ecom.prodcutservice.security;

import com.ecom.prodcutservice.product.dtos.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class Incomingcalls implements WebMvcConfigurer {
    @Autowired
    private RestClient restClient;

    public ProductResponseDto fetchProduct(long productId, String jwtToken) {
        return restClient.get()
                .uri("http://localhost:8085/cart/getByid/" + productId)
                .headers(headers -> headers.setBearerAuth(jwtToken)) // Set Bearer Token
                .retrieve()
                .body(ProductResponseDto.class);
    }
    private String getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
            return jwtAuth.getToken().getTokenValue();
        }
        throw new IllegalStateException("JWT token is not available");
    }
    @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedOrigins("*");
            System.out.println("ENTERED BY CART SERVICE");
        }

}
