package orderservice.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerss {
    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<ExceptionMessageDto> getExceptionForSignup(SignUpException e,WebRequest webRequest){
    ExceptionMessageDto dto=new ExceptionMessageDto(
            e.getMessage()+" PLEASE SIGN UP ",
            404,
            LocalDateTime.now()
    );
            return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CannotFetchDataFromUserService.class)
    public ResponseEntity getFetchDataException(CannotFetchDataFromUserService e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

// order cannot placed exception
    @ExceptionHandler(OrderCannotPLacedexception.class)
    public ResponseEntity<ExceptionMessageDto> didnotgetCartData(OrderCannotPLacedexception e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

}
