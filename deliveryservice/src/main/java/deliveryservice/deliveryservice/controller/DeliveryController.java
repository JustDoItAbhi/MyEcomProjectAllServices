package deliveryservice.deliveryservice.controller;

import deliveryservice.deliveryservice.dto.DeliveryResponseDto;
import deliveryservice.deliveryservice.dto.DeliveryServiceNotification;
import deliveryservice.deliveryservice.entity.Delivery;
import deliveryservice.deliveryservice.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/delevery")
public class DeliveryController {
    private DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/notifyPaymentDetails")
    public ResponseEntity<Delivery> getpaymenStatus(@RequestBody DeliveryResponseDto dto){
        return ResponseEntity.ok(deliveryService.getNotification(dto));
    }
    @GetMapping("/")
    public ResponseEntity<List<Delivery>> getAll(){
        return ResponseEntity.ok(deliveryService.getsll());
    }
}
