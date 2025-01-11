package deliveryservice.deliveryservice.servicesproject.exceptions;

import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CartNotFount;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.UserNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MessageHandler {
    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<MessageResponseDto> userNotExits(UserNotExistsException e){
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
