package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.UserRequestDto;
import deliveryservice.deliveryservice.dto.UserResponseDto;
import deliveryservice.deliveryservice.entity.UserAddress;
import deliveryservice.deliveryservice.entity.UserResponseUpdatedEntity;
import deliveryservice.deliveryservice.exceptions.UserNotExistsExcetion;
import deliveryservice.deliveryservice.mapper.UserMapper;
import deliveryservice.deliveryservice.repository.UserAddressRepository;
import deliveryservice.deliveryservice.template.CallingUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServices{
private UserAddressRepository userAddressRepository;
private CallingUserService callingUserService;
private UserResponseUpdateRepository updateRepository;

    public UserServiceImpl(UserAddressRepository userAddressRepository, CallingUserService callingUserService, UserResponseUpdateRepository updateRepository) {
        this.userAddressRepository = userAddressRepository;
        this.callingUserService = callingUserService;
        this.updateRepository = updateRepository;
    }

    @Override
    public UserResponseDto getUser(String userEmail) throws UserNotExistsExcetion {
        UserResponseDto existingUser=callingUserService.getUser(userEmail);
        if(existingUser==null){
            throw new UserNotExistsExcetion("PLEASE SIGN UP "+userEmail);
        }
//      UserAddress userAddress= UserMapper.fromEntity(existingUser);
//        userAddressRepository.save(userAddress);
        return existingUser;
    }


    @Override
    public UserResponseUpdatedEntity updateUser(String email, UserRequestDto dto) throws UserNotExistsExcetion {
        UserResponseDto existingUser=callingUserService.getUser(email);
        if(existingUser==null){
            throw new UserNotExistsExcetion("PLEASE SIGN UP  "+email);
        }
        UserResponseUpdatedEntity userAddress=new UserResponseUpdatedEntity();
        userAddress.setUpadatedUserEmail(email);
        userAddress.setCustomerName(dto.getCustomerName());
        userAddress.setCustomerPhone(dto.getCustomerPhone());
        userAddress.setCustomerHouseNumber(dto.getCustomerHouseNumber());
        userAddress.setCustomerStreet(dto.getCustomerStreet());
        userAddress.setCustomerLandMark(dto.getCustomerLandMark());
        userAddress.setCustomerCity(dto.getCustomerCity());
        userAddress.setCustomerState(dto.getCustomerState());
        userAddress.setCustomerCountry(dto.getCustomerCountry());
        userAddress.setCustomerPostelCode(dto.getCustomerPostelCode());
        updateRepository.save(userAddress);
        return userAddress;
    }
}
