package com.ecom.productservice.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionsHnadlers {// GLOBAL EXCEPTION HANDLER
    private LocalDateTime localDateTime;
    @ExceptionHandler(ProductNotFoundException.class)// PRODUCT NOT FOUND EXCEPTION
    public ResponseEntity<MessageResponse> getException(ProductNotFoundException e){
        MessageResponse response=new MessageResponse(
                e.getMessage(),
                401,
               LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
