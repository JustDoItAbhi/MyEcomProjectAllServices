package orderservice.users.usermapper;


import orderservice.entity.UserDetails;
import orderservice.users.userdtos.UserResponseDto;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDetails fromEntity(UserResponseDto dto){// USER MAPPER FROM RESPONSE DTO TO USER ENTITY
            UserDetails details=new UserDetails();
            details.setUserId(dto.getUserId());
            details.setUserEmail(dto.getUserEmail());
            details.setUserName(dto.getUserName());
            details.setUserPhone(dto.getUserPhone());
//            details.setUserPassword(dto.getUserPassword());
            details.setUserLandMark(dto.getUserLandMark());
            details.setUserStreet(dto.getUserStreet());
            details.setUserHouseNumber(dto.getUserHouseNumber());
            details.setUserPostelCode(dto.getUserPostelCode());
            details.setUserState(dto.getUserState());
            details.setUserCountry(dto.getUserCountry());
            details.setUserCity(dto.getUserCity());
            List<String>roles=new ArrayList<>();
            for(String roles1:dto.getRolesList()){
                roles.add(roles1);
            }
            details.setRolesList(roles);
            return details;
    }
}
