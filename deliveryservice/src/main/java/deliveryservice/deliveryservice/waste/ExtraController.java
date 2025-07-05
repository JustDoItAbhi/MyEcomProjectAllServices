package deliveryservice.deliveryservice.waste;

import deliveryservice.deliveryservice.servicesproject.dtos.DeliveryResponseDto;
import deliveryservice.deliveryservice.servicesproject.entity.Delivery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delevery")
public class ExtraController {
    private DeliveryService deliveryService;

    public ExtraController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/notifyPaymentDetails")
    public ResponseEntity<Delivery> getpaymenStatus(@RequestBody DeliveryResponseDto dto) {
        return ResponseEntity.ok(deliveryService.getNotification(dto));
    }
//    @GetMapping("/{cartId}")
//    public ResponseEntity<CartResposneDtos>getCart(@PathVariable("cartId")long cartId){
//        return ResponseEntity.ok(deliveryService.fetchCart(cartId));
//    }

}
