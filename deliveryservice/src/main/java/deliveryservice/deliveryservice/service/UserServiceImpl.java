package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.UserRequestDto;
import deliveryservice.deliveryservice.dto.UserResponseDto;
import deliveryservice.deliveryservice.entity.Destinations;
import deliveryservice.deliveryservice.entity.UserAddress;
import deliveryservice.deliveryservice.entity.UserResponseUpdatedEntity;
import deliveryservice.deliveryservice.exceptions.CityNotFound;
import deliveryservice.deliveryservice.exceptions.CountryNotFound;
import deliveryservice.deliveryservice.exceptions.UserNotExistsExcetion;
import deliveryservice.deliveryservice.mapper.UserMapper;
import deliveryservice.deliveryservice.repository.DestinationRespository;
import deliveryservice.deliveryservice.repository.UserAddressRepository;
import deliveryservice.deliveryservice.template.CallingUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserServices{
private UserAddressRepository userAddressRepository;
private CallingUserService callingUserService;
private UserResponseUpdateRepository updateRepository;
private DestinationRespository destinationRespository;

    public UserServiceImpl(UserAddressRepository userAddressRepository, CallingUserService callingUserService,
                           UserResponseUpdateRepository updateRepository, DestinationRespository destinationRespository) {
        this.userAddressRepository = userAddressRepository;
        this.callingUserService = callingUserService;
        this.updateRepository = updateRepository;
        this.destinationRespository = destinationRespository;
    }

    @Override
    public UserAddress getUser(String userEmail) throws UserNotExistsExcetion, CountryNotFound, CityNotFound {
        UserResponseDto existingUser = callingUserService.getUser(userEmail);
        if (existingUser == null) {
            throw new UserNotExistsExcetion("PLEASE SIGN UP " + userEmail);
        }

        Destinations destinations = destinationRespository.findByCountryAndCity(
                existingUser.getUserCountry(),
                existingUser.getUserCity());
        if (destinations == null) {
            throw new CityNotFound("COUNTRY NOT FOUND " + existingUser.getUserCountry() + " " + existingUser.getUserCity());
        }
        existingUser.setCreatedAt(LocalDateTime.now());
        existingUser.setUpdatedAt(LocalDateTime.now());
        existingUser.setUserEmail(userEmail);
        existingUser.setUserPassword("NOT VISIBLE BECAUSE OF PRIVECY RASONS");
        double normalDelivery = Math.ceil(((double) destinations.getCountryDistance()/500) );
        int expressDelivery = destinations.getCountryDistance() / 120;
        if (normalDelivery <= 1) {
            existingUser.setMessage("PARCEL WILL DELIVER IN MAXIMUM 2 days " );
        } else {

                existingUser.setMessage("PARCEL WILL DELIVER IN "+((int)normalDelivery+1)+" days" );
            }
//        if(destinations.getCountryDistance()/120<=24){
//            destinations.setMessage("EXPRESS your order will deliver in  ONE DAYS maximum total travel from lviv to "
//                    +destinations.getCity()+" is "+destinations.getCountryDistance());
//        }
            return UserMapper.fromEntity(existingUser);
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
