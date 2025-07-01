package com.ecommer.userservices.users.usercontroller;


import com.ecommer.userservices.exceptions.NotCorrectEmailProvidedException;
import com.ecommer.userservices.exceptions.SignUpUserException;
import com.ecommer.userservices.users.userdtos.*;
import com.ecommer.userservices.users.userservices.UserServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;// autowired user service
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com)$";

    @PostMapping("/signup")// (public use) to sign up as a new user
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignUp signUp) throws JsonProcessingException {
        if(!signUp.getUserEmail().matches(EMAIL_PATTERN)){
            throw new NotCorrectEmailProvidedException("Invalid email! Only Gmail and Yahoo emails are allowed."+signUp.getUserEmail());
        }
        return ResponseEntity.ok(userServices.signUp(signUp));
    }
    @PreAuthorize("isAuthenticated())")
    @PostMapping("/login")// authenticated api
    public ResponseEntity<UserResponseDto> login(@RequestBody Login login) {// LOGIN USER IF AUTHENTICATED
        return ResponseEntity.ok(userServices.logIn(login));
    }
    @PreAuthorize("isAuthenticated())")
    @GetMapping("/logout")//// authenticated api
    public ResponseEntity<String> logout(@RequestBody LogOut logOut){
        return new ResponseEntity<>(userServices.logOut(logOut), HttpStatus.OK);
    }

    @PutMapping("/update/email/{email}")//// authenticated api
    public ResponseEntity<UserResponseDto> updatDate(@PathVariable ("email")String email, @RequestBody UpdateUserRequestDto dto)  {
        return ResponseEntity.ok(userServices.updateUser(email, dto));
    }
    @PreAuthorize("isAuthenticated())")
     @GetMapping("/resetpassword/{email}/{passoword}")//// authenticated api
    public ResponseEntity<ResponseEntity<UserResponseDto>> resetPassword(@PathVariable ("email")String email,
                                                                         @PathVariable ("passoword")String password){
        return ResponseEntity.ok(userServices.resetPassword(email,password));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/debug") // ONLY ADMIN CAN USE
    public ResponseEntity<String> debugAdminRole() {//manually debug for authentication and retrieving the roles,
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")// ONLY ADMIN CAN USE
    public ResponseEntity<?> deleteById(@PathVariable("id") long id) {
        if (!userServices.deleteUser(id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("id cannot be deleted"));
        } else {
            userServices.deleteUser(id);
            return ResponseEntity.ok(new MessageResponse("id deleted"));
        }
    }
    @PreAuthorize("isAuthenticated())")
    @GetMapping("/getUserByid/{email}")// authenticated api
    public ResponseEntity<UserResponseDto> getByEmail(@PathVariable ("email")String email) throws SignUpUserException {// GET USER BY ITS EMAIL
        return ResponseEntity.ok(userServices.getById(email));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getallUsers")/// ADMIN api
    public ResponseEntity<List<UserResponseDto>> getAll() {// TO GET ALL THE USERS
        return ResponseEntity.ok(userServices.getAllUsers());
    }
}
