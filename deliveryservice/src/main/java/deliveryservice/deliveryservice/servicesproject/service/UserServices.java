package deliveryservice.deliveryservice.servicesproject.service;

import deliveryservice.deliveryservice.servicesproject.dto.requests.UserRequestDto;
import deliveryservice.deliveryservice.servicesproject.dto.UserResponseDto;
import deliveryservice.deliveryservice.servicesproject.entity.UserAddress;
import deliveryservice.deliveryservice.servicesproject.entity.UserResponseUpdatedEntity;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.UserNotExistsException;

import java.util.List;

public interface UserServices {// STRATRGY AND LAYER PATTERN FOR USER SERVICE
    UserAddress getUser(long cartId, String userEmail) throws UserNotExistsException, CountryNotFound, CityNotFound;
    UserResponseUpdatedEntity updateUser(String email, UserRequestDto dto) throws UserNotExistsException;
    List<UserAddress>getAll();// GET ALL USER ADDRESS
    UserResponseDto FetchUserDataAndValidate(String email);// TEST USER DATA
    boolean deleteDeliveryAddress(long id);// DELETE USER

}
