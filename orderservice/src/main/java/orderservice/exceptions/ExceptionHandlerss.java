package orderservice.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerss {// GLOABLE EXECPTION HANDLER
    @ExceptionHandler(SignUpException.class)// SIGN UP EXECPTION
    public ResponseEntity<ExceptionMessageDto> getExceptionForSignup(SignUpException e,WebRequest webRequest){
   Map<String,Object>signup=new LinkedHashMap<>();
   signup.put("userName","**");
   signup.put("userPhone","**");
   signup.put("userPassword","**");
   signup.put("userEmail","**");
   signup.put("roles","[**]");
   signup.put("userHouseNumber","**");
   signup.put("userStreet","**");
   signup.put("userLandMark","**");
   signup.put("city","**");
   signup.put("state","**");
   signup.put("country","**");
   signup.put("postelCode","**");
    ExceptionMessageDto dto=new ExceptionMessageDto(
            e.getMessage(),
            404,
            LocalDateTime.now(),
            signup

    );
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CannotFetchDataFromUserService.class)// USER DATA NOT FOUND EXECPTION
    public ResponseEntity getFetchDataException(CannotFetchDataFromUserService e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

// order cannot placed exception
    @ExceptionHandler(OrderCannotPLacedexception.class)// ORDER CANNOT PLACED EXECPTION
    public ResponseEntity<ExceptionMessageDto> didnotgetCartData(OrderCannotPLacedexception e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

}
