package com.ecommer.userservices.security.auth2server.customization;

import com.ecommer.userservices.entity.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@JsonDeserialize
@NoArgsConstructor
public class CustomiseGrandAuthority implements GrantedAuthority {
    @JsonProperty("authority")
    private String grantAuthority;
    public CustomiseGrandAuthority(String roles) {
        System.out.println("ROLE IS HERE _-----------------"+ roles);
        this.grantAuthority = roles;
    }
    @Override
    public String getAuthority() {
        return this.grantAuthority;
    }
}
