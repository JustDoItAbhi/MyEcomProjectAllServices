package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.UserRequestDto;
import deliveryservice.deliveryservice.dto.UserResponseDto;
import deliveryservice.deliveryservice.entity.UserAddress;
import deliveryservice.deliveryservice.entity.UserResponseUpdatedEntity;
import deliveryservice.deliveryservice.exceptions.CityNotFound;
import deliveryservice.deliveryservice.exceptions.CountryNotFound;
import deliveryservice.deliveryservice.exceptions.UserNotExistsExcetion;

public interface UserServices {
    UserAddress getUser(long cartId,String userEmail) throws UserNotExistsExcetion, CountryNotFound, CityNotFound;
    UserResponseUpdatedEntity updateUser(String email, UserRequestDto dto) throws UserNotExistsExcetion;

}
