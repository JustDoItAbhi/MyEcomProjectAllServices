package deliveryservice.deliveryservice.controller;

import deliveryservice.deliveryservice.dto.DeliveryResponseDto;
import deliveryservice.deliveryservice.dto.DeliveryServiceNotification;
import deliveryservice.deliveryservice.entity.Delivery;
import deliveryservice.deliveryservice.entity.UserAddress;
import deliveryservice.deliveryservice.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
