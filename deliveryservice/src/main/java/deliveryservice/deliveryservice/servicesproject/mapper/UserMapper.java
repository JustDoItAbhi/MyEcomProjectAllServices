package deliveryservice.deliveryservice.servicesproject.mapper;

import deliveryservice.deliveryservice.servicesproject.entity.UserAddress;
import deliveryservice.deliveryservice.servicesproject.dtos.UserResponseDto;
import deliveryservice.deliveryservice.servicesproject.orderservice.dtos.CheckOutOrder;
import deliveryservice.deliveryservice.servicesproject.orderservice.dtos.UserDto;

import java.time.LocalDateTime;

public class UserMapper {
    public static UserAddress fromEntity(UserResponseDto users){// MAPPING FROM RESPONSE DTO TO USER ENTITY
        UserAddress responseDto=new UserAddress();
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
//        responseDto.setUpdatedAt(users.getUpdatedAt());
        responseDto.setCartId(users.getCartId());
        responseDto.setTotalAmount(users.getTotalAmount());
        return responseDto;
    }
    public static UserAddress fromOrderService(CheckOutOrder users){// MAPPING FROM RESPONSE DTO TO USER ENTITY
        UserAddress responseDto=new UserAddress();
        responseDto.setStatus(users.getStatus());
        responseDto.setCreatedAt(LocalDateTime.now());
        responseDto.setCartId(users.getCartId());
        responseDto.setTotalAmount(users.getPrice());
        UserDto userDto= users.getUserDto();
        responseDto.setMessage(userDto.getMessage());
        responseDto.setUserName(userDto.getUserName());
        responseDto.setUserPhone(userDto.getUserPhone());
        responseDto.setUserEmail(userDto.getUserEmail());
        responseDto.setUserPassword("NOT VISIBLE BECAUSE OF PRIVCY RASONS");
        responseDto.setUserCity(userDto.getUserCity());
        responseDto.setUserCountry(userDto.getUserCountry());
        responseDto.setUserState(userDto.getUserState());
        responseDto.setUserPostelCode(userDto.getUserPostelCode());
        responseDto.setUserHouseNumber(userDto.getUserHouseNumber());
        responseDto.setUserStreet(userDto.getUserStreet());
        responseDto.setUserLandMark(userDto.getUserLandMark());
        responseDto.setCountryDistance(userDto.getCountryDistance());

//        responseDto.setUpdatedAt(users.getUpdatedAt());

        return responseDto;
    }
}
