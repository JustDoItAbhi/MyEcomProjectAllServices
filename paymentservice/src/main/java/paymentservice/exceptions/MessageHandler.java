package paymentservice.exceptions;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MessageHandler {
    private LocalDateTime localDateTime;
@ExceptionHandler(PaymentConfirmed.class)
    public ResponseEntity<MessageResponseDto> confimedPayment(PaymentConfirmed e){
    MessageResponseDto responseDto=new MessageResponseDto(
            e.getMessage(),
            200,
             LocalDateTime.now()
    );
    return new  ResponseEntity<>(responseDto, HttpStatus.OK);
}

    @ExceptionHandler(OrderNotFetchedException.class)
    public ResponseEntity<MessageResponseDto> orderNotFetchedExceptionResponseEntity(OrderNotFetchedException e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageResponseDto> userNotFound(UserNotFoundException e){
        MessageResponseDto responseDto=new MessageResponseDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }


}
