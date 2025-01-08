package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.CartResposneDtos;
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
import deliveryservice.deliveryservice.repository.UserResponseUpdateRepository;
import deliveryservice.deliveryservice.template.CallingCartdata;
import deliveryservice.deliveryservice.template.CallingUserService;
import deliveryservice.deliveryservice.template.NominatimClinet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Primary
public class UserServiceImpl implements UserServices{
private UserAddressRepository userAddressRepository;
private CallingUserService callingUserService;
private DestinationRespository destinationRespository;
private UserResponseUpdateRepository updateRepository;
private CallingCartdata callingCartdata;

    public UserServiceImpl(UserAddressRepository userAddressRepository, CallingUserService callingUserService,
                           DestinationRespository destinationRespository,
                           UserResponseUpdateRepository updateRepository, CallingCartdata callingCartdata) {
        this.userAddressRepository = userAddressRepository;
        this.callingUserService = callingUserService;
        this.destinationRespository = destinationRespository;
        this.updateRepository = updateRepository;
        this.callingCartdata = callingCartdata;
    }

    @Override
    public UserAddress getUser(long cartId,String userEmail) throws UserNotExistsExcetion, CountryNotFound, CityNotFound {
        UserResponseDto existingUser = callingUserService.getUser(userEmail);// fetching user from user service
        if (existingUser == null) { //validation for user
            throw new UserNotExistsExcetion("PLEASE SIGN UP " + userEmail);
        }

        // fetching cart service
        CartResposneDtos dtos=callingCartdata.fetchingFromCartServcie(cartId);
        // validation of cart service
        if(dtos==null){
            throw new RuntimeException(" CART NOT FOUND "+cartId);
        }
        existingUser.setCartId(dtos.getCartId());// saved cartId in UserAdrress
        existingUser.setTotalAmount(dtos.getTotal());// save amount in UserAdrress

        // fetching country and city from database
        Destinations destinations = destinationRespository.findByCountryAndCity(
                existingUser.getUserCountry(),
                existingUser.getUserCity());
        // validting city and country
        if (destinations == null) {
            throw new CityNotFound("COUNTRY NOT FOUND " + existingUser.getUserCountry() + " " + existingUser.getUserCity());
        }


        existingUser.setUserPassword("NOT VISIBLE BECAUSE OF PRIVECY RASONS");// delivery service dont have rights to check password
        existingUser.setCountryDistance(destinations.getCountryDistance());// setting deistanc in km to useraddress
        if (destinations.getCountryDistance()/60<=24)   {// if delivery speed 60 km per hour and it will reach before or = 24 hours
            existingUser.setMessage("PARCEL WILL DELIVER IN MAXIMUM 2 days " );// then take maximum two days for delivery
        }else{
            existingUser.setMessage("PARCEL WILL DELIVER IN MAXIMUM 7 DAYS AS YOUR CITY IS FAR "// otherwise 7 days
                    +existingUser.getUserCity()+" is "+destinations.getCountryDistance());
        }
        existingUser.setCreatedAt(LocalDateTime.now());// created timestamp
        existingUser.setUpdatedAt(LocalDateTime.now());// updated timestamp
        existingUser.setUserEmail(userEmail);// set user email
           UserAddress userAddress= userAddressRepository.save(UserMapper.fromEntity(existingUser));// save to database
            return userAddress;
    }


    @Override
    public UserResponseUpdatedEntity updateUser(String email, UserRequestDto dto) throws UserNotExistsExcetion {// update only address if user want to send parcel to some other place
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
