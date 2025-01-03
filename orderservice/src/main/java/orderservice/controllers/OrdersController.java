package orderservice.controllers;

import orderservice.dtos.OrderResponseDto;
import orderservice.entity.Orders;
import orderservice.exceptions.SignUpException;
import orderservice.services.OrderItemServices;
import orderservice.services.UserServices;
import orderservice.users.UserDetails;
import orderservice.users.userdtos.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    public OrderItemServices orderItemServices;
    @Autowired
    private UserServices userServices;

    @GetMapping("/getCartById/{email}/{cartId}")
    public ResponseEntity<OrderResponseDto> getCART(@PathVariable("email") String email,
                                                    @PathVariable("cartId") long cartId)throws SignUpException {
        return ResponseEntity.ok(orderItemServices.getCartItems(email, cartId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable("id") long id) {
        return ResponseEntity.ok(orderItemServices.deleteOrder(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable("id") long id) {
        return ResponseEntity.ok(orderItemServices.getOrderById(id));
    }

    @GetMapping("/GETUSERROLE")
    public ResponseEntity<String> getUSERrOLE() {
        return ResponseEntity.ok(orderItemServices.getUserRoles());
    }

    @GetMapping("/getUserForDelivery/{email}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable("email") String email) {
        return ResponseEntity.ok(userServices.getUserByEmail(email));

    }
}
