package deliveryservice.deliveryservice.servicesproject.service;

import deliveryservice.deliveryservice.servicesproject.dto.CartResposneDtos;
import deliveryservice.deliveryservice.servicesproject.dto.UserRequestDto;
import deliveryservice.deliveryservice.servicesproject.dto.UserResponseDto;
import deliveryservice.deliveryservice.servicesproject.entity.UserAddress;
import deliveryservice.deliveryservice.servicesproject.entity.UserResponseUpdatedEntity;

import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.UserNotExistsException;
import deliveryservice.deliveryservice.servicesproject.mapper.UserMapper;
import deliveryservice.deliveryservice.servicesproject.repository.DestinationRespository;
import deliveryservice.deliveryservice.servicesproject.repository.UserAddressRepository;
import deliveryservice.deliveryservice.servicesproject.repository.UserResponseUpdateRepository;
import deliveryservice.deliveryservice.servicesproject.template.CallingServices;
import deliveryservice.deliveryservice.servicesproject.template.CallingUserService;
import deliveryservice.deliveryservice.servicesproject.template.NominatimClinet;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Service
//@Primary
// in memory
public class UserServiceWithNominatim implements UserServices{
    private final UserAddressRepository userAddressRepository;
    private final CallingUserService callingUserService;
    private final UserResponseUpdateRepository updateRepository;
    private final DestinationRespository destinationRespository;
    private final CallingServices callingCartdata;
    private NominatimClinet clinet;
    private final Map<Long, UserAddress> userAddressCache;

    public UserServiceWithNominatim(DestinationRespository destinationRespository, UserAddressRepository userAddressRepository,
                                    CallingUserService callingUserService, UserResponseUpdateRepository updateRepository,
                                    CallingServices callingCartdata, NominatimClinet clinet, Map<Long, UserAddress> userAddressCache) {
        this.destinationRespository = destinationRespository;
        this.userAddressRepository = userAddressRepository;
        this.callingUserService = callingUserService;
        this.updateRepository = updateRepository;
        this.callingCartdata = callingCartdata;
        this.clinet = clinet;
        this.userAddressCache = userAddressCache;
    }

    @Override
    @Transactional
    public UserAddress getUser(long cartId, String userEmail) throws UserNotExistsException, CountryNotFound, CityNotFound {
        UserResponseDto existingUser = callingUserService.getUser(userEmail);
        if (existingUser == null) {
            throw new UserNotExistsException("PLEASE SIGN UP " + userEmail);
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
    public UserResponseUpdatedEntity updateUser(String email, UserRequestDto dto) throws UserNotExistsException {
        UserResponseDto existingUser=callingUserService.getUser(email);
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
        updateRepository.save(userAddress);
        return userAddress;
    }

    @Override
    public List<UserAddress> getAll() {
        List<UserAddress>userAddressList=userAddressRepository.findAll();
        return userAddressList;
    }

    @Override
    public UserResponseDto FetchUserDataAndValidate(String email) {
        return null;
    }

    @Override
    public boolean deleteDeliveryAddress(long id) {
        userAddressRepository.deleteById(id);
        return true;
    }


}
