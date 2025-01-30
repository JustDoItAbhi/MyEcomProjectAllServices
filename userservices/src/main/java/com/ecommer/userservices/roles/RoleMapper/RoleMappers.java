package com.ecommer.userservices.roles.RoleMapper;

import com.ecommer.userservices.entity.Roles;
import com.ecommer.userservices.roles.roledtos.RoleResponseDto;

public class RoleMappers {// role mapper class for convertion deom entity to resposen dto
    public static RoleResponseDto fromEntity(Roles roles){// converting role entity to response dto
        RoleResponseDto responseDto=new RoleResponseDto();
        responseDto.setRoleId(roles.getRoleId());
        responseDto.setRole(roles.getRoleType());
//        responseDto.setUsersId(roles.getUsersList());
        return responseDto;
    }
}
