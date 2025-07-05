package deliveryservice.deliveryservice.servicesproject.controller;

import deliveryservice.deliveryservice.servicesproject.dto.CheckOutOrder;
import deliveryservice.deliveryservice.servicesproject.dto.requests.UserRequestDto;
import deliveryservice.deliveryservice.servicesproject.dto.UserResponseDto;
import deliveryservice.deliveryservice.servicesproject.entity.UserAddress;
import deliveryservice.deliveryservice.servicesproject.entity.UserResponseUpdatedEntity;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.UserNotExistsException;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.servicesproject.service.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deliveryUser")
public class DeliveryController {// CONTROLLER
    private UserServices userServices;// DECLARATION OF USER SERVICE
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com)$";// INSTANCE TO VALIDATE EMAIL

    public DeliveryController(UserServices userServices) {// DEPENDECY INJECTION
        this.userServices = userServices;
    }

    @GetMapping("/{cartId}/{userEmail}")// GET CART BY CART ID AND USER EMAIL
    public ResponseEntity<UserAddress> loginByEmail(@PathVariable("cartId")long cartId, @PathVariable("userEmail")String userEmail) throws UserNotExistsException, CityNotFound, CountryNotFound {
       if(!userEmail.matches(EMAIL_PATTERN)){// VALIDATE EMAIL
           throw new UserNotExistsException("Invalid email! Only Gmail and Yahoo emails are allowed."+userEmail);
       }
        return ResponseEntity.ok(userServices.getUser(cartId,userEmail));
    }
    @PutMapping("/update/{email}")// UPDATE USER ADDRESS IF USER WANT ALTERNATIVE ADDRESS
    public ResponseEntity<UserResponseUpdatedEntity>updateUserAddress(@PathVariable("email")String email , @RequestBody UserRequestDto dto) throws UserNotExistsException {
        if(!email.matches(EMAIL_PATTERN)){// VALIDATE EMAIL
            throw new UserNotExistsException("Invalid email! Only Gmail and Yahoo emails are allowed."+email);
        }
        return ResponseEntity.ok(userServices.updateUser(email,dto));
}
@GetMapping("/getllDeliveries")// GET ALL DELIVERY ORDERS
    public ResponseEntity<List<UserAddress>>getAllDeliveryAdress(){
        return ResponseEntity.ok(userServices.getAll());
}
@DeleteMapping("/{id}")// DELETE DELIVERY
    public ResponseEntity<Boolean>deleteDeliveryAddress(@PathVariable("id")long id){
        if(id<=0){
            throw new UserNotExistsException("USER ID NOT FOUND "+id);
        }
        return ResponseEntity.ok(userServices.deleteDeliveryAddress(id));
}

@GetMapping("/debug") // admin// CHECK ROLE BY TOKEN
public ResponseEntity<String> debugAdminRole() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authenticated");
    }
    // Retrieve roles from authentication
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    String roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(", "));

    return ResponseEntity.ok("User roles: " + roles);
}
    @GetMapping("/getOrder/{email}")// GET USR BY EMAIL
    public ResponseEntity<CheckOutOrder> getOrder(@PathVariable ("email")String email){
        if(!email.matches(EMAIL_PATTERN)){// VALIDATE EMAIL
            throw new UserNotExistsException("Invalid email! Only Gmail and Yahoo emails are allowed."+email);
        }
        return ResponseEntity.ok(userServices.getOrderDetails(email));
    }
//@GetMapping("/{email}")// GET USR BY EMAIL
//    public ResponseEntity<UserResponseDto> getOnlyUser(@PathVariable ("email")String email){
//    if(!email.matches(EMAIL_PATTERN)){// VALIDATE EMAIL
//        throw new UserNotExistsException("Invalid email! Only Gmail and Yahoo emails are allowed."+email);
//    }
//        return ResponseEntity.ok(userServices.FetchUserDataAndValidate(email));
//}
//    @GetMapping("/{country}")
//    public ResponseEntity<List<Destinations>> getByCountry(@PathVariable("country")String country) throws CountryNotFound {
//        return ResponseEntity.ok(userServices.getDestinationByCounty(country));
//    }
//    @GetMapping("/{city}")
//    public ResponseEntity<Destinations>getByCity(@PathVariable("city")String city) throws CityNotFound {
//        return ResponseEntity.ok(userServices.getDestinationBycity(city));
//    }

}
