package com.ecommer.userservices.security.auth2server.RegeredClientServices;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterOidcClientRequestDto {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String postLogoutRedirectUri;
    private boolean requireAuthorizationConsent;
    private Set<String> scopes;
}
