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
    private long roleId;
    private String role;
    private Users usersId;

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

    public Users getUsersId() {
        return usersId;
    }

    public void setUsersId(Users usersId) {
        this.usersId = usersId;
    }

    public static RoleResponseDto fromEntity(Roles roles){
        RoleResponseDto responseDto=new RoleResponseDto();
        responseDto.setRoleId(roles.getId());
        responseDto.setRole(roles.getRoleType());
        responseDto.setUsersId(roles.getUsersList());
        return responseDto;
    }
}
