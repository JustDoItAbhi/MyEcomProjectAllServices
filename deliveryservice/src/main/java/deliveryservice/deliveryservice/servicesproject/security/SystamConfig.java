package deliveryservice.deliveryservice.servicesproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SystamConfig {// SYSTEM CONFIGRATION
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize -> authorize
//                                .requestMatchers("/deliveryUser/{cartId}/{userEmail}").hasRole("ADMIN")
//                                .requestMatchers("/user/getUserByid/**").hasRole("ADMIN")
//                                .requestMatchers("/user/getUserByid/**").authenticated()
//                                .requestMatchers("/deliveryUser/{cartId}/{userEmail}").permitAll()
//                        .requestMatchers("/deliveryUser/{id}").authenticated()
                                .anyRequest().authenticated()// PROTECTED ALL
                        )
                )
  .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );
//                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
    @Bean// FACTORY DESGIN PETTERN FRO ROLE
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_"); // Ensure consistency
        authoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return authenticationConverter;
    }

}
