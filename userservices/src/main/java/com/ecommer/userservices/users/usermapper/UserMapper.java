package com.ecommer.userservices.users.usermapper;

import com.ecommer.userservices.entity.Roles;
import com.ecommer.userservices.entity.Users;
import com.ecommer.userservices.users.userdtos.UserResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponseDto fromEntity(Users users){
//        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        UserResponseDto responseDto=new UserResponseDto();
        responseDto.setUserId(users.getId());
        responseDto.setUserName(users.getUserName());
        responseDto.setUserPhone(users.getUserPhone());
        responseDto.setUserPassword(users.getUserPassword());
        responseDto.setUserEmail(users.getUserEmail());
        responseDto.setUserCity(users.getUserCity());
        responseDto.setUserCountry(users.getUserCountry());
        responseDto.setUserState(users.getUserState());
        responseDto.setUserPostelCode(users.getUserPostelCode());
        responseDto.setUserHouseNumber(users.getUserHouseNumber());
        responseDto.setUserStreet(users.getUserStreet());
        responseDto.setUserLandMark(users.getUserLandMark());
        List<String> rolesList = users.getRolesList().stream()
                .map(Roles::getRoleType) // Extracting role type
                .collect(Collectors.toList());

        responseDto.setRolesList(rolesList); // Assuming role
        return responseDto;
    }
}
