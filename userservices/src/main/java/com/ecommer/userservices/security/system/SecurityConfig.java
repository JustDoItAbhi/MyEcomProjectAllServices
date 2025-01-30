package com.ecommer.userservices.security.system;

import com.ecommer.userservices.security.auth2server.customization.*;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.client.RestClient;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


    @Configuration
    @EnableWebSecurity
    public class SecurityConfig   {
            @Bean//spring bean of annotaiton
            @Order(1)// order placed as of importance of method to run as lower number mean high priority
            public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)//
                    throws Exception {
                //default configrations
                OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
                        // Enable OpenID Connect (OIDC) support with the default settings.
                        // This configures the OAuth2 authorization server to support OIDC.
                http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                        .oidc(Customizer.withDefaults());	// Enable OpenID Connect
                http
                        // exception handling
                        .exceptionHandling((exceptions) -> exceptions
                                .defaultAuthenticationEntryPointFor(
                                        new LoginUrlAuthenticationEntryPoint("/login"),
                                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                                )
                        )
                        // Accept access tokens for User Info and/or Client Registration and jwt suppport
                        .oauth2ResourceServer((resourceServer) -> resourceServer
                                .jwt(Customizer.withDefaults()));

                return http.build();
            }

        @Bean
        @Order(2)// 2nd level order for spring method
        public SecurityFilterChain protectedFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()  // Disable CSRF for API
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(HttpMethod.POST,"/user/signup").permitAll()// for public use
                        .requestMatchers(HttpMethod.POST,"/role/**").hasRole("ADMIN")// only admin can send post request
                                    .requestMatchers("/user/login").authenticated()// only authenticated user can login
                            .requestMatchers(HttpMethod.GET,"/user/debug").hasAnyRole("ADMIN","USER")// debug to check what role user have
                            .requestMatchers(HttpMethod.GET, "/user/getallUsers").hasRole("ADMIN")// only admin can send get all user request
                            .requestMatchers(HttpMethod.GET,"/user/delete/").hasRole("ADMIN")// only admin can delete a user
                            .requestMatchers("/user/getUserByid/{email}").hasAnyRole("ADMIN","USER")// all users will be permitted
                                    .anyRequest().authenticated()// rest all apis are authenticated
                    )
                    // jwt role base configrations
                    .oauth2ResourceServer(oauth2 -> oauth2
                            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))

                    )

                    .formLogin(Customizer.withDefaults());// default login
            return http.build();
        }

        @Bean
        public RoleHierarchy roleHierarchy() {// mark admin as high priority always then user
            String hierarchy = "ROLE_ADMIN > ROLE_USER";
            RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
            roleHierarchy.setHierarchy(hierarchy);
            return roleHierarchy;
        }



        @Bean
        public JWKSource<SecurityContext> jwkSource() {// jwt source for key setup and security context
            KeyPair keyPair = generateRsaKey();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)// private key for jwt
                    .keyID(UUID.randomUUID().toString())// rendom id
                    .build();
            JWKSet jwkSet = new JWKSet(rsaKey);
            return new ImmutableJWKSet<>(jwkSet);
        }

        @Bean
        public JwtAuthenticationConverter jwtAuthenticationConverter() {// role based jwt recogoniser as if ROLE_ADMIN or ADMIN
            JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
            authoritiesConverter.setAuthorityPrefix("ROLE_"); // Ensure consistency
            authoritiesConverter.setAuthoritiesClaimName("roles");

            JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
            authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
            return authenticationConverter;
        }
        @Bean
        public HttpFirewall allowSemicolonFirewall() {// Allow semicolons in URLs
            StrictHttpFirewall firewall = new StrictHttpFirewall();
            firewall.setAllowSemicolon(true);
            return firewall;
        }

        private static KeyPair generateRsaKey() {// GENERATED key pairs
            KeyPair keyPair;
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048);
                keyPair = keyPairGenerator.generateKeyPair();
            }
            catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
            return keyPair;
        }

        @Bean
        public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {// jwt decoder
            return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
        }

        @Bean
        public AuthorizationServerSettings authorizationServerSettings() {// authorization end point
            return AuthorizationServerSettings.builder()
                    .issuer("http://localhost:8090")// Issuer URL for OAuth2
                    .build();
        }
        @Bean
        public BCryptPasswordEncoder encoder(){// password encoder bean method
            return new BCryptPasswordEncoder();
        }
        @Bean//by me
        public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {// jwt adding claims
            return (context) -> {
                if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                    context.getClaims().claims((claims) -> {
                        Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
                                .stream()
                                .map(c -> c.replaceFirst("^ROLE_", ""))
                                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                        claims.put("roles", roles);
                        Authentication authentication = context.getPrincipal();
                        Long userId = ((CustomUsersDetals)authentication.getPrincipal()).getUserId();
                        claims.put("userId",userId);// aditional id implementation in jwt token
                    });

                }
            };
        }
    }

