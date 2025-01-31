package deliveryservice.deliveryservice.waste;

import deliveryservice.deliveryservice.servicesproject.dto.DeliveryResponseDto;
import deliveryservice.deliveryservice.servicesproject.entity.Delivery;

public interface DeliveryService {
    Delivery getNotification(DeliveryResponseDto dto);
//    List<Delivery>getsll();
//    CartResposneDtos fetchCart(long cartId);
}
