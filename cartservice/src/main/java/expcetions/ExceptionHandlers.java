package expcetions;

import org.apache.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ExceptionMessageDto> getExceptionForSignup(CartNotFoundException e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto, HttpStatusCode.valueOf(HttpStatus.SC_BAD_GATEWAY));
    }

}
