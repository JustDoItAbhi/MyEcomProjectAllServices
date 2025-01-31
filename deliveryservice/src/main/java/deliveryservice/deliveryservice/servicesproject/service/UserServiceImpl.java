package deliveryservice.deliveryservice.servicesproject.service;

import deliveryservice.deliveryservice.servicesproject.dto.CartResposneDtos;
import deliveryservice.deliveryservice.servicesproject.dto.UserRequestDto;
import deliveryservice.deliveryservice.servicesproject.dto.UserResponseDto;
import deliveryservice.deliveryservice.servicesproject.entity.Destinations;
import deliveryservice.deliveryservice.servicesproject.entity.UserAddress;
import deliveryservice.deliveryservice.servicesproject.entity.UserResponseUpdatedEntity;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CartNotFount;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.UserNotExistsException;
import deliveryservice.deliveryservice.servicesproject.mapper.UserMapper;
import deliveryservice.deliveryservice.servicesproject.repository.DestinationRespository;
import deliveryservice.deliveryservice.servicesproject.repository.UserAddressRepository;
import deliveryservice.deliveryservice.servicesproject.repository.UserResponseUpdateRepository;
import deliveryservice.deliveryservice.servicesproject.template.CallingServices;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Primary// priotities service bean
public class UserServiceImpl implements UserServices{// STRATRGY AND LAYER PATTERN FOR USER SERVICE IMPLEMENTATION
private final UserAddressRepository userAddressRepository;// DECLARED USERADRESS REPOSIOTORY
private final CallingServices callingServices;// DECLARED RESTTEMPLATE REPOSIOTORY
private final DestinationRespository destinationRespository;// DECLARED DESTINATAION REPOSIOTORY
private final UserResponseUpdateRepository updateRepository;// DECLARED ADDRESS UPDATE REPOSIOTORY
private final Map<Long, UserResponseDto> userAddressCache=new HashMap<>();// OPTIONAL USE TO REDUCE TIME COMPLEXITY
// CONTRUCTOR
public UserServiceImpl(UserAddressRepository userAddressRepository, CallingServices callingServices,
                           DestinationRespository destinationRespository, UserResponseUpdateRepository updateRepository) {
        this.userAddressRepository = userAddressRepository;
        this.callingServices = callingServices;
        this.destinationRespository = destinationRespository;
        this.updateRepository = updateRepository;
    }

    @Override// GET CART AND USER BY IDS
    public UserAddress getUser(long cartId, String userEmail) throws UserNotExistsException, CountryNotFound, CityNotFound {
        UserResponseDto existingUser =FetchUserDataAndValidate(userEmail);// FETCH USER DATA
        CartResposneDtos dtos=fetchCartAndValidate(cartId);// FETCH CART DATA
        if(dtos==null){// VALIDATE CART
            throw new CartNotFount("YOU ARE NOT ALLOWED "+ userEmail);
        }
        existingUser.setCartId(dtos.getCartId());// saved cartId in UserAdrress
        existingUser.setTotalAmount(dtos.getTotal());// save amount in UserAdrress
        // fetching country and city from database
        Destinations destinations = fetchCountriesAndCities(existingUser.getUserCountry(),existingUser.getUserCity());
//        existingUser.setCountryDistance(destinations.getCountryDistance());// setting deistanc in km to useraddress
        if (destinations.getCountryDistance()/60<=24)   {// if delivery speed 60 km per hour and it will reach before or = 24 hours
            existingUser.setMessage("PARCEL WILL DELIVER IN MAXIMUM 2 days " );// then take maximum two days for delivery
        }else{
            existingUser.setMessage("PARCEL WILL DELIVER IN MAXIMUM 7 DAYS AS YOUR CITY IS FAR "// otherwise 7 days
                    +existingUser.getUserCity()+" is "+destinations.getCountryDistance()+" killometers");
        }


        existingUser.setCreatedAt(LocalDateTime.now());// created timestamp
        existingUser.setUpdatedAt(LocalDateTime.now());// updated timestamp
        existingUser.setUserEmail(userEmail);// set user email
        existingUser.setCountryDistance(destinations.getCountryDistance());

        UserAddress responseDto= UserMapper.fromEntity(existingUser);


        userAddressCache.put(cartId, existingUser);
        System.out.println("USER ADDRESS IS "+existingUser.getUserEmail());
        userAddressRepository.save(responseDto);// SAVE TO DATABASE
            return responseDto;
    }

    public UserResponseDto FetchUserDataAndValidate(String email){
        UserResponseDto existingUser = callingServices.getUser(email);// fetching user from user service
        if (existingUser == null) { //validation for user
            throw new UserNotExistsException("PLEASE SIGN UP " + email);
        }
        return existingUser;
    }
    private CartResposneDtos fetchCartAndValidate(long cartId){// FETCH CART BY ID
        CartResposneDtos dtos=callingServices.fetchingFromCartServcie(cartId);
        if(dtos==null){
            throw new CartNotFount(" CART NOT FOUND "+cartId);
        }
        return dtos;
    }
    private Destinations fetchCountriesAndCities(String country,String city) throws CityNotFound {// FETCH CITY AND COUNTRY
        Destinations destinations = destinationRespository.findByCountryAndCity(country,city);
        if (destinations == null) {// VALIDATE
            throw new CityNotFound("COUNTRY NOT FOUND " + country + " " + city);
        }
        return destinations;
    }

    @Override// UPDATE ADDRESS
    public UserResponseUpdatedEntity updateUser(String email, UserRequestDto dto) throws UserNotExistsException {// update only address if user want to send parcel to some other place
        UserResponseDto existingUser= callingServices.getUser(email);
        if(existingUser==null){
            throw new UserNotExistsException("PLEASE SIGN UP  "+email);
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
        updateRepository.save(userAddress);// SAVE TO DATABASE
        return userAddress;
    }

    @Override
    public List<UserAddress> getAll() {// GET ALL SAVED USERS
        List<UserAddress>userAddressList=userAddressRepository.findAll();
        return userAddressList;
    }

    @Override
    public boolean deleteDeliveryAddress(long id) {// DELETE DELIVERY
        userAddressRepository.deleteById(id);
        return true;
    }


}
