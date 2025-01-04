package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.UserRequestDto;
import deliveryservice.deliveryservice.dto.UserResponseDto;
import deliveryservice.deliveryservice.entity.UserAddress;
import deliveryservice.deliveryservice.entity.UserResponseUpdatedEntity;
import deliveryservice.deliveryservice.exceptions.UserNotExistsExcetion;
import org.apache.catalina.User;

public interface UserServices {
    UserResponseDto getUser(String userEmail) throws UserNotExistsExcetion;
    UserResponseUpdatedEntity updateUser(String email, UserRequestDto dto) throws UserNotExistsExcetion;
}
