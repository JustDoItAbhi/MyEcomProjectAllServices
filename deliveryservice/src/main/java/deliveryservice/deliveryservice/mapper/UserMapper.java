package deliveryservice.deliveryservice.mapper;

import deliveryservice.deliveryservice.entity.UserAddress;
import deliveryservice.deliveryservice.dto.UserResponseDto;

public class UserMapper {
    public static UserAddress fromEntity(UserResponseDto users){
        UserAddress responseDto=new UserAddress();
        responseDto.setId(users.getUserId());
        responseDto.setUserName(users.getUserName());
        responseDto.setUserPhone(users.getUserPhone());
        responseDto.setUserEmail(users.getUserEmail());
        responseDto.setUserPassword("NOT VISIBLE BECAUSE OF PRIVCY RASONS");
        responseDto.setUserCity(users.getUserCity());
        responseDto.setUserCountry(users.getUserCountry());
        responseDto.setUserState(users.getUserState());
        responseDto.setUserPostelCode(users.getUserPostelCode());
        responseDto.setUserHouseNumber(users.getUserHouseNumber());
        responseDto.setUserStreet(users.getUserStreet());
        responseDto.setUserLandMark(users.getUserLandMark());
        responseDto.setMessage(users.getMessage());
        responseDto.setCountryDistance(users.getCountryDistance());
        responseDto.setCreatedAt(users.getCreatedAt());
        responseDto.setUpdatedAt(users.getUpdatedAt());
        responseDto.setCartId(users.getCartId());
        responseDto.setTotalAmount(users.getTotalAmount());
        return responseDto;
    }
}
