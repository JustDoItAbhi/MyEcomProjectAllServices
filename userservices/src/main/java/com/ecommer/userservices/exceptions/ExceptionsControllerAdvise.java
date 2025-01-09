package com.ecommer.userservices.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionsControllerAdvise {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageResponseDto> userNotFound(UserNotFoundException e){
        MessageResponseDto dto=new MessageResponseDto(
                e.getMessage(),
                404,
                LocalDateTime.now()

        );
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<MessageResponseDto> emailNotFound(EmailNotFoundException e){
        MessageResponseDto dto=new MessageResponseDto(
                e.getMessage(),
                404,
                LocalDateTime.now()

        );
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RoleNotFoundExceptions.class)
    public ResponseEntity<MessageResponseDto>roleNotFound(RoleNotFoundExceptions e){
        MessageResponseDto dto=new MessageResponseDto(
                e.getMessage(),
                404,
                LocalDateTime.now()

        );
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(SignUpUserException.class)
    public ResponseEntity<MessageResponseDto>signupexpention(SignUpUserException e){
        MessageResponseDto dto=new MessageResponseDto(
                e.getMessage()+"PLEASE GO TO USER API AND SIGN UP ",
                404,
                LocalDateTime.now()

        );
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<MessageResponseDto>userAlreadyExits(UserAlreadyExists e){
        MessageResponseDto dto=new MessageResponseDto(
                e.getMessage()+"PLEASE GO TO USER API AND SIGN UP ",
                404,
                LocalDateTime.now()

        );
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
