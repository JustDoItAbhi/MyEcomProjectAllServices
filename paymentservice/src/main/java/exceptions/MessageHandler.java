package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;

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

}
