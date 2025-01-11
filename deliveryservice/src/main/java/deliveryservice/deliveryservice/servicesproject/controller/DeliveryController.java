package deliveryservice.deliveryservice.servicesproject.controller;

import deliveryservice.deliveryservice.servicesproject.dto.CartResposneDtos;
import deliveryservice.deliveryservice.servicesproject.dto.DeliveryResponseDto;
import deliveryservice.deliveryservice.servicesproject.entity.Delivery;
import deliveryservice.deliveryservice.servicesproject.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delevery")
public class DeliveryController {
    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/notifyPaymentDetails")
    public ResponseEntity<Delivery> getpaymenStatus(@RequestBody DeliveryResponseDto dto) {
        return ResponseEntity.ok(deliveryService.getNotification(dto));
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResposneDtos>getCart(@PathVariable("cartId")long cartId){
        return ResponseEntity.ok(deliveryService.fetchCart(cartId));
    }

}
