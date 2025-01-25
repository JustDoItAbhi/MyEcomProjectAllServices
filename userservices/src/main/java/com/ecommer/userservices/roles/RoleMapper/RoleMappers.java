package com.ecommer.userservices.roles.RoleMapper;

import com.ecommer.userservices.entity.Roles;
import com.ecommer.userservices.roles.roledtos.RoleResponseDto;

public class RoleMappers {
    public static RoleResponseDto fromEntity(Roles roles){
        RoleResponseDto responseDto=new RoleResponseDto();
        responseDto.setRoleId(roles.getRoleId());
        responseDto.setRole(roles.getRoleType());
//        responseDto.setUsersId(roles.getUsersList());
        return responseDto;
    }
}
