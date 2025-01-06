package deliveryservice.deliveryservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MessageHandler {
    private LocalDateTime localDateTime;
    @ExceptionHandler(UserNotExistsExcetion.class)
    public ResponseEntity<MessageResponseDto> cityNotExits(UserNotExistsExcetion e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage()+"PLEASE SIGN UP",
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CountryNotFound.class)
    public ResponseEntity<MessageResponseDto> countryNotExits(CountryNotFound e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage()+"ORDER CAN ONLY DELIVER IN EU COUNTRIES",
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CityNotFound.class)
    public ResponseEntity<MessageResponseDto> cityNotExits(CountryNotFound e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage()+"PLEASE CHOOSE CAPITAL CITY ONLY",
                200,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
