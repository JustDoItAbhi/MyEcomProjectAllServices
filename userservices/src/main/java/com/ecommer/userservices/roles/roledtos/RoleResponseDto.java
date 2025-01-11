package com.ecommer.userservices.roles.roledtos;

import com.ecommer.userservices.entity.Roles;
import com.ecommer.userservices.entity.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RoleResponseDto {
    private long roleId;
    private String role;
    private String usersEmail;

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

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
    }

    public static RoleResponseDto fromEntity(Roles roles){
        RoleResponseDto responseDto=new RoleResponseDto();
        responseDto.setRoleId(roles.getId());
        responseDto.setRole(roles.getRoleType());
        responseDto.setUsersEmail(roles.getUsersEmail());
        return responseDto;
    }
}
