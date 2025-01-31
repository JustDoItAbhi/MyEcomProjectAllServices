package orderservice.controllers;

import orderservice.dtos.OrderResponseDto;
import orderservice.entity.Orders;
import orderservice.exceptions.SignUpException;
import orderservice.services.OrderItemServices;
import orderservice.services.UserServices;
import orderservice.entity.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    public OrderItemServices orderItemServices;
    @Autowired
    private UserServices userServices;
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com)$";//  EMAIL INSTANCE FOR GMAIL OR YAHOO MAIL ONLY

    @GetMapping("/getCartById/{cartId}")// first api to call cart service
    public ResponseEntity<OrderResponseDto> getCART(@PathVariable("cartId") long cartId)throws SignUpException {
        return ResponseEntity.ok(orderItemServices.getCartItems(cartId));
    }

    @DeleteMapping("/{id}")// delete order
    public ResponseEntity<Boolean> deleteOrder(@PathVariable("id") long id) {
        return ResponseEntity.ok(orderItemServices.deleteOrder(id));
    }

    @GetMapping("/{id}")// confirm order by id
    public ResponseEntity<Orders> confirmOrderById(@PathVariable("id") long id) {
        return ResponseEntity.ok(orderItemServices.getOrderById(id));
    }

    @GetMapping("/GETUSERROLE")// for personal use to check role
    public ResponseEntity<String> getUSERrOLE() {
        return ResponseEntity.ok(orderItemServices.getUserRoles());
    }

    @GetMapping("/getUserForDelivery/{email}")// get user by email
    public ResponseEntity<UserDetails> getUserById(@PathVariable("email") String email) {
        if(!email.matches(EMAIL_PATTERN)){
            throw new UsernameNotFoundException("Invalid email! Only Gmail and Yahoo emails are allowed."+email);
        }
        return ResponseEntity.ok(userServices.getUserByEmail(email));

    }
}
