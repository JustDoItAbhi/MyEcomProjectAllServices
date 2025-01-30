package com.ecommer.userservices.users.usercontroller;


import com.ecommer.userservices.exceptions.SignUpUserException;
import com.ecommer.userservices.users.userdtos.*;
import com.ecommer.userservices.users.userservices.UserServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;// autowired user service

    @PostMapping("/signup")// public use to sign up as a new user
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignUp signUp) throws JsonProcessingException {
        return ResponseEntity.ok(userServices.signUp(signUp));
    }
    @PostMapping("/login")// authenticated api
    public ResponseEntity<UserResponseDto> login(@RequestBody Login login) {// LOGIN USER IF AUTHENTICATED
        return ResponseEntity.ok(userServices.logIn(login));
    }
    @GetMapping("/logout/{email}")//// authenticated api
    public ResponseEntity<UserResponseDto> logout(@RequestBody LogOut logOut){
        return new ResponseEntity<>(userServices.logOut(logOut), HttpStatus.OK);
    }

    @PutMapping("/update/email/{email}")//// authenticated api
    public ResponseEntity<UserResponseDto> updatDate(@PathVariable ("email")String email, @RequestBody UpdateUserRequestDto dto)  {
        return ResponseEntity.ok(userServices.updateUser(email, dto));
    }
     @GetMapping("/resetpassword/{email}/{passoword}")//// authenticated api
    public ResponseEntity<ResponseEntity<UserResponseDto>> resetPassword(@PathVariable ("email")String email,
                                                                         @PathVariable ("passoword")String password){
        return ResponseEntity.ok(userServices.resetPassword(email,password));
    }
    @GetMapping("/debug") // ONLY ADMIN CAN USE
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

        return ResponseEntity.ok("User roles: " + roles);// CHECK ROLE
    }

    @DeleteMapping("/delete/{id}")// ONLY ADMIN CAN USE
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        if (!userServices.deleteUser(id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("id cannot be deleted"));
        } else {
            userServices.deleteUser(id);
            return ResponseEntity.ok(new MessageResponse("id deleted"));
        }
    }
    @GetMapping("/getUserByid/{email}")// authenticated api
    public ResponseEntity<UserResponseDto> getAll(@PathVariable ("email")String email) throws SignUpUserException {// GET USER BY ITS EMAIL
        return ResponseEntity.ok(userServices.getById(email));
    }
    @GetMapping("/getallUsers")/// ADMIN api
    public ResponseEntity<List<UserResponseDto>> getAll() {// TO GET ALL THE USERS
        return ResponseEntity.ok(userServices.getAllUsers());
    }

}
