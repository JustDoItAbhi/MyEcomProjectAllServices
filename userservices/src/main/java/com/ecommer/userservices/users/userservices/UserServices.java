package com.ecommer.userservices.users.userservices;

import com.ecommer.userservices.exceptions.SignUpUserException;
import com.ecommer.userservices.users.userdtos.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServices {
    UserResponseDto signUp(SignUp signUp) throws JsonProcessingException;// SIGN UP
    UserResponseDto logIn(Login login);// USER LOGIN
    String logOut(LogOut logOut);// USER LOGOUT
    List<UserResponseDto>getAllUsers();// GET ALL USERS
    boolean deleteUser(long id);// DELETE A USER
    UserResponseDto getById(String email) throws SignUpUserException;// GET USER BY EMAIL
    UserResponseDto updateUser(String email, UpdateUserRequestDto dto);// UPDATE USER
    ResponseEntity<UserResponseDto> resetPassword(String email,String password);// RESET PASSWORD
}
