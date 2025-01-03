package deliveryservice.deliveryservice.mapper;

import deliveryservice.deliveryservice.entity.UserAddress;
import deliveryservice.deliveryservice.template.UserResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserAddress fromEntity(UserResponseDto users){
        UserAddress responseDto=new UserAddress();
        responseDto.setId(users.getUserId());
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
        return responseDto;
    }
}
