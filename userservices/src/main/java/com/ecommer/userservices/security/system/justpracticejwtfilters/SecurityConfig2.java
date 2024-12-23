//package com.ecommer.userservices.security.auth2server.system.justpracticejwtfilters;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig2 {
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public SecurityConfig2(JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .requestMatchers("/user/signup","/user/login","/user/resetpassword/**").permitAll()  // Adjust the path for public endpoints
//                .anyRequest().authenticated();  // All other requests require authentication
//
//        return http.build();
//    }
//}
