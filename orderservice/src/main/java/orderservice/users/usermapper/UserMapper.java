package orderservice.users.usermapper;


import orderservice.dtos.CheckOutOrder;
import orderservice.dtos.OrderResponseDto;
import orderservice.dtos.UserDto;
import orderservice.entity.UserDetails;
import orderservice.users.userdtos.UserResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDetails fromEntity(UserResponseDto dto){// USER MAPPER FROM RESPONSE DTO TO USER ENTITY
            UserDetails details=new UserDetails();
            details.setUserId(dto.getUserId());
            details.setCreatedAt(LocalDateTime.now());
            details.setUserEmail(dto.getUserEmail());
            details.setUserName(dto.getUserName());
            details.setUserPhone(dto.getUserPhone());
            details.setUserPassword(dto.getUserPassword());
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
    public static CheckOutOrder fromUserResponse(UserResponseDto dto, OrderResponseDto responseDto){
            CheckOutOrder check=new CheckOutOrder();
            check.setCartId(responseDto.getCartId());
            check.setOrderStatus(responseDto.getOrderStatus());
            check.setOrderId(responseDto.getOrderid());
            check.setPrice(responseDto.getPrice());
            UserDto userDto=new UserDto();
            userDto.setUserId(dto.getUserId());
            userDto.setUserName(dto.getUserName());
            userDto.setUserPhone(dto.getUserPhone());
            userDto.setUserEmail(dto.getUserEmail());
            userDto.setUserHouseNumber(dto.getUserHouseNumber());
            userDto.setUserStreet(dto.getUserStreet());
            userDto.setUserCity(dto.getUserCity());
            userDto.setUserState(dto.getUserState());
            userDto.setUserCountry(dto.getUserCountry());
            userDto.setUserPostelCode(dto.getUserPostelCode());
            userDto.setUserLandMark(dto.getUserLandMark());
//            userDto.setTotalAmount(userDto.getTotalAmount());
            check.setUserDto(userDto);
            return check;
    }
}
