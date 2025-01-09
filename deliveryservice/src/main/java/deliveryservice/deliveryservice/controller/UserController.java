package deliveryservice.deliveryservice.controller;

import deliveryservice.deliveryservice.dto.UserRequestDto;
import deliveryservice.deliveryservice.dto.UserResponseDto;
import deliveryservice.deliveryservice.entity.UserAddress;
import deliveryservice.deliveryservice.entity.UserResponseUpdatedEntity;
import deliveryservice.deliveryservice.exceptions.exceptionfiles.UserNotExistsExcetion;
import deliveryservice.deliveryservice.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.service.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveryUser")
public class UserController {
    private UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/{cartId}/{userEmail}")
    public ResponseEntity<UserAddress> loginByEmail(@PathVariable("cartId")long cartId, @PathVariable("userEmail")String userEmail) throws UserNotExistsExcetion, CityNotFound, CountryNotFound {
        return ResponseEntity.ok(userServices.getUser(cartId,userEmail));
    }
    @PutMapping("/update/{email}")
    public ResponseEntity<UserResponseUpdatedEntity>updateUserAddress(@PathVariable("email")String email , @RequestBody UserRequestDto dto) throws UserNotExistsExcetion {
        return ResponseEntity.ok(userServices.updateUser(email,dto));
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
