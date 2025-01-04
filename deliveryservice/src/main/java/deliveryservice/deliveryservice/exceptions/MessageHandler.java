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
    public ResponseEntity<MessageResponseDto> userNotExits(UserNotExistsExcetion e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage()+"PLEASE SIGN UP",
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

}
