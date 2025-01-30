package com.ecommer.userservices.roles.roledtos;

import com.ecommer.userservices.entity.Roles;
import com.ecommer.userservices.entity.Users;
import com.ecommer.userservices.users.userdtos.UserResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class RoleResponseDto {
    private long roleId;// role id
    private String role;// role name
// getters and setters
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
