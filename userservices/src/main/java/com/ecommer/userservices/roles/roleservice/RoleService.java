package com.ecommer.userservices.roles.roleservice;

import com.ecommer.userservices.roles.roledtos.RoleRequestDto;
import com.ecommer.userservices.roles.roledtos.RoleResponseDto;

import java.util.List;

public interface RoleService {// role service
    RoleResponseDto createRole(RoleRequestDto requestDto);// method interface to create a role
    boolean deleteRole(long roleId,long userId);// method interface to delete a role
    List<RoleResponseDto> getAllRoles();// get all roles

}
