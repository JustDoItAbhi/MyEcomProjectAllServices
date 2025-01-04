package deliveryservice.deliveryservice.controller;

import deliveryservice.deliveryservice.dto.UserResponseDto;
import deliveryservice.deliveryservice.entity.UserAddress;
import deliveryservice.deliveryservice.exceptions.UserNotExistsExcetion;
import deliveryservice.deliveryservice.service.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deliveryUser")
public class UserController {
    private UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }
    @GetMapping("/{userEmail}")
    public ResponseEntity<UserResponseDto> loginByEmail(@PathVariable("userEmail")String userEmail) throws UserNotExistsExcetion {
        return ResponseEntity.ok(userServices.getUser(userEmail));
    }
}
