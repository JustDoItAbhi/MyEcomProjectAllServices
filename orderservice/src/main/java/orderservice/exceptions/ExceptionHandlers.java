package orderservice.exceptions;

import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<ExceptionMessageDto> getExceptionForSignup(SignUpException e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto, HttpStatusCode.valueOf(HttpStatus.SC_BAD_GATEWAY));
    }

    @ExceptionHandler(CannotFetchDataFromUserService.class)
    public ResponseEntity<ExceptionMessageDto> getFetchDataException(CannotFetchDataFromUserService e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto, HttpStatusCode.valueOf(HttpStatus.SC_BAD_GATEWAY));
    }


    @ExceptionHandler(OrderCannotPLacedexception.class)
    public ResponseEntity<ExceptionMessageDto> didnotgetCartData(OrderCannotPLacedexception e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto, HttpStatusCode.valueOf(HttpStatus.SC_BAD_GATEWAY));
    }

}
