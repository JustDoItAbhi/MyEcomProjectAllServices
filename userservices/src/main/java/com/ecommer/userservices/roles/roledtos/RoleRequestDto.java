package com.ecommer.userservices.roles.roledtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;


public class RoleRequestDto {// role request dto
    @NotNull
    private String role;// role name

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
