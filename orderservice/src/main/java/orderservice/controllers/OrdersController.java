package orderservice.controllers;

import orderservice.dtos.OrderResponseDto;
import orderservice.entity.Orders;
import orderservice.services.OrderItemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrdersController {
@Autowired
    public OrderItemServices orderItemServices;

@GetMapping("/getCartById/{userId}")
public ResponseEntity<OrderResponseDto> getCART(@PathVariable("userId") String userId){
    return ResponseEntity.ok(orderItemServices.getCartItems(userId));
}
@DeleteMapping("/{id}")
public ResponseEntity<Boolean> deleteOrder(@PathVariable ("id")long id){
    return ResponseEntity.ok(orderItemServices.deleteOrder(id));
}
@GetMapping("/{id}")
public ResponseEntity<Orders> getOrderById(@PathVariable("id") long id){
    return ResponseEntity.ok(orderItemServices.getById(id));
}
    @GetMapping("/GETUSERROLE")
    public ResponseEntity<String> getUSERrOLE() {
        return ResponseEntity.ok(orderItemServices.getUserRoles());
    }
}
