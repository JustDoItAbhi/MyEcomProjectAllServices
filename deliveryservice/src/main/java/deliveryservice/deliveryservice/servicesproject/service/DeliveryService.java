package deliveryservice.deliveryservice.servicesproject.service;

import deliveryservice.deliveryservice.servicesproject.dto.CartResposneDtos;
import deliveryservice.deliveryservice.servicesproject.dto.DeliveryResponseDto;
import deliveryservice.deliveryservice.servicesproject.entity.Delivery;

import java.util.List;

public interface DeliveryService {
    Delivery getNotification(DeliveryResponseDto dto);
    List<Delivery>getsll();
    CartResposneDtos fetchCart(long cartId);
}
