package com.ecommer.userservices.users.userservices;

import com.ecommer.userservices.exceptions.SignUpUserException;
import com.ecommer.userservices.users.userdtos.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServices {
    UserResponseDto signUp(SignUp signUp) throws JsonProcessingException;
    UserResponseDto logIn(Login login);
    UserResponseDto logOut(LogOut logOut);
    List<UserResponseDto>getAllUsers();
    boolean deleteUser(long id);
    UserResponseDto getById(String email) throws SignUpUserException;
    UserResponseDto updateUser(String email, UpdateUserRequestDto dto);
    ResponseEntity<UserResponseDto> resetPassword(String email,String password);
}
