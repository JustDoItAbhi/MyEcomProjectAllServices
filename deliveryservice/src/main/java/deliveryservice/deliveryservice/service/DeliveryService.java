package deliveryservice.deliveryservice.service;

import deliveryservice.deliveryservice.dto.DeliveryResponseDto;
import deliveryservice.deliveryservice.entity.Delivery;

import java.util.List;

public interface DeliveryService {
    Delivery getNotification(DeliveryResponseDto dto);
    List<Delivery>getsll();
}
