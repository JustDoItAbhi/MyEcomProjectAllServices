package deliveryservice.deliveryservice.servicesproject.exceptions;

import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CartNotFount;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CityNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.CountryNotFound;
import deliveryservice.deliveryservice.servicesproject.exceptions.exceptionfiles.UserNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MessageHandler {
    @ExceptionHandler(UserNotExistsException.class)// USER NOT FOUND EXECPTION
    public ResponseEntity<MessageResponseDto> userNotExits(UserNotExistsException e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage(),
                404
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CountryNotFound.class)// COUNTRY NOT FOUND EXECPTION
    public ResponseEntity countryNotExits(CountryNotFound e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage(),
                404
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CityNotFound.class)// CITY NOT FOUND EXECPTION
    public ResponseEntity<MessageResponseDto> cityNotExits(CountryNotFound e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage(),
                200
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    //cart not found exception
    @ExceptionHandler(CartNotFount.class)// CART NOT FOUND EXECPTION
    public ResponseEntity<MessageResponseDto> cartNotExits(CartNotFount e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage(),
                404
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
