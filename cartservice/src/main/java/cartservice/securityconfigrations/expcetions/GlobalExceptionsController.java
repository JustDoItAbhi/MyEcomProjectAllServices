package cartservice.securityconfigrations.expcetions;


import cartservice.securityconfigrations.expcetions.expectionsfiles.CartNotFoundException;
import cartservice.securityconfigrations.expcetions.expectionsfiles.InvalidEmailException;
import cartservice.securityconfigrations.expcetions.expectionsfiles.ProductAlreadyExists;
import cartservice.securityconfigrations.expcetions.expectionsfiles.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionsController {// GLOBAL EXECPTION HANDLER
    @ExceptionHandler(CartNotFoundException.class)// CART NOT FOUND EXCEPTION
    public ResponseEntity<Map<String,Object>> getCartNotFoundException(Exception e){
        Map<String,Object>response=new HashMap<>();
        response.put("message ",e.getMessage());
        response.put("error code ",e.getClass().getName());
        response.put("atTime ",LocalDateTime.now());

        return new  ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    //if product not found exception
    @ExceptionHandler(ProductNotFoundException.class)// PRODUCT NOT FOUND EXECPTION
    public ResponseEntity<ExceptionMessageDto> getExceptionForProduct(ProductNotFoundException e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }
    // product already exits in cart
    @ExceptionHandler(ProductAlreadyExists.class)//PRODUCT ALREADY EXISTS
    public ResponseEntity<ExceptionMessageDto> productAlreadyExists(ProductAlreadyExists e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }
    // user not found exception
    @ExceptionHandler(UsernameNotFoundException.class)// USER NOT FOUND EXECPTION
    public ResponseEntity<ExceptionMessageDto> UserNotFound(UsernameNotFoundException e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }
    // invalid email  exception
    @ExceptionHandler(InvalidEmailException.class)// USER NOT FOUND EXECPTION
    public ResponseEntity<ExceptionMessageDto> UserNotFound(InvalidEmailException e){
        ExceptionMessageDto dto=new ExceptionMessageDto(
                e.getMessage(),
                404,
                LocalDateTime.now()
        );
        return new  ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }

}
