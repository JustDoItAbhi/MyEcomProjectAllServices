package deliveryservice.deliveryservice.servicesproject.controller;

import deliveryservice.deliveryservice.servicesproject.dto.UserRequestDto;
import deliveryservice.deliveryservice.servicesproject.entity.UserAddress;
import deliveryservice.deliveryservice.servicesproject.entity.UserResponseUpdatedEntity;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.UserNotExistsException;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.servicesproject.service.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveryUser")
public class UserController {
    private UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/{cartId}/{userEmail}")
    public ResponseEntity<UserAddress> loginByEmail(@PathVariable("cartId")long cartId, @PathVariable("userEmail")String userEmail) throws UserNotExistsException, CityNotFound, CountryNotFound {
        return ResponseEntity.ok(userServices.getUser(cartId,userEmail));
    }
    @PutMapping("/update/{email}")
    public ResponseEntity<UserResponseUpdatedEntity>updateUserAddress(@PathVariable("email")String email , @RequestBody UserRequestDto dto) throws UserNotExistsException {
        return ResponseEntity.ok(userServices.updateUser(email,dto));
}
@GetMapping("/getllDeliveries")
    public ResponseEntity<List<UserAddress>>getAllDeliveryAdress(){
        return ResponseEntity.ok(userServices.getAll());
}
@DeleteMapping("/{id}")
    public ResponseEntity<Boolean>deleteDeliveryAddress(@PathVariable("id")long id){
        if(id<=0){
            throw new UserNotExistsException("USER ID NOT FOUND "+id);
        }
        return ResponseEntity.ok(userServices.deleteDeliveryAddress(id));
}



//    @GetMapping("/{country}")
//    public ResponseEntity<List<Destinations>> getByCountry(@PathVariable("country")String country) throws CountryNotFound {
//        return ResponseEntity.ok(userServices.getDestinationByCounty(country));
//    }
//    @GetMapping("/{city}")
//    public ResponseEntity<Destinations>getByCity(@PathVariable("city")String city) throws CityNotFound {
//        return ResponseEntity.ok(userServices.getDestinationBycity(city));
//    }

}
