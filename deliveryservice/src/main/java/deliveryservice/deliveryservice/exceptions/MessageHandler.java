package deliveryservice.deliveryservice.exceptions;

import deliveryservice.deliveryservice.exceptions.exceptionfiles.CartNotFount;
import deliveryservice.deliveryservice.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.exceptions.exceptionfiles.UserNotExistsExcetion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MessageHandler {


    @ExceptionHandler(UserNotExistsExcetion.class)
    public ResponseEntity<MessageResponseDto> userNotExits(UserNotExistsExcetion e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage(),
                404
//                LocalDateTime.now()
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CountryNotFound.class)
    public ResponseEntity countryNotExits(CountryNotFound e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage(),
                404
//                LocalDateTime.now()
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CityNotFound.class)
    public ResponseEntity<MessageResponseDto> cityNotExits(CountryNotFound e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage(),
                200
//                LocalDateTime.now()
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    //cart not found exception
    @ExceptionHandler(CartNotFount.class)
    public ResponseEntity<MessageResponseDto> cartNotExits(CartNotFount e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage(),
                404
//                LocalDateTime.now()
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
