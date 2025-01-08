package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.CartResposneDtos;
import deliveryservice.deliveryservice.dto.Coordinates;
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
import jakarta.transaction.Transactional;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Map;

// in memory
public class UserServiceWithNominatim implements UserServices{
    private final UserAddressRepository userAddressRepository;
    private final CallingUserService callingUserService;
    private final UserResponseUpdateRepository updateRepository;
    private final DestinationRespository destinationRespository;
    private final CallingCartdata callingCartdata;
    private NominatimClinet clinet;
    private final Map<Long, UserAddress> userAddressCache;

    public UserServiceWithNominatim(UserAddressRepository userAddressRepository, CallingUserService callingUserService,
                                    UserResponseUpdateRepository updateRepository, DestinationRespository destinationRespository,
                                    CallingCartdata callingCartdata, Map<Long, UserAddress> userAddressCache, NominatimClinet clinet) {
        this.userAddressRepository = userAddressRepository;
        this.callingUserService = callingUserService;
        this.updateRepository = updateRepository;
        this.destinationRespository = destinationRespository;
        this.callingCartdata = callingCartdata;
        this.userAddressCache = userAddressCache;
        this.clinet = clinet;
    }

    @Override
    @Transactional
    public UserAddress getUser(long cartId, String userEmail) throws UserNotExistsExcetion, CountryNotFound, CityNotFound {
        UserResponseDto existingUser = callingUserService.getUser(userEmail);
        if (existingUser == null) {
            throw new UserNotExistsExcetion("PLEASE SIGN UP " + userEmail);
        }
        CartResposneDtos dtos=callingCartdata.fetchingFromCartServcie(cartId);
        if(dtos==null){
            throw new RuntimeException(" CART NOT FOUND "+cartId);
        }
        existingUser.setCartId(dtos.getCartId());
        existingUser.setTotalAmount(dtos.getTotal());

        existingUser.setCreatedAt(LocalDateTime.now());
        existingUser.setUpdatedAt(LocalDateTime.now());
        existingUser.setUserEmail(userEmail);

        double distance  =clinet.calculateDistance(existingUser.getUserCity(),existingUser.getUserCountry());

        existingUser.setUserPassword("NOT VISIBLE BECAUSE OF PRIVECY RASONS");
        double totaltimetakenfroDelivery=distance/60.0;

        existingUser.setCountryDistance((int)distance);
        if (totaltimetakenfroDelivery<=24)   {
            existingUser.setMessage("PARCEL WILL DELIVER IN MAXIMUM 2 days " );
        }else{
            existingUser.setMessage("PARCEL WILL DELIVER IN MAXIMUM 7 DAYS AS YOUR CITY IS FAR "
                    +existingUser.getUserCity()+" is "+distance);
        }
        UserAddress userAddress = UserMapper.fromEntity(existingUser);// in memory
        userAddressCache.put(cartId, userAddress);
        System.out.println("USER ADDRESS IS "+userAddress);
        return userAddress;
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
