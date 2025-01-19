package com.ecommer.userservices.roles.roleservice;

import com.ecommer.userservices.roles.roledtos.RoleRequestDto;
import com.ecommer.userservices.roles.roledtos.RoleResponseDto;

import java.util.List;

public interface RoleService {
    RoleResponseDto createRole(RoleRequestDto requestDto);
    boolean deleteRole(long roleId,long userId);
    List<RoleResponseDto> getAllRoles();

}
