package deliveryservice.deliveryservice.servicesproject.orderservice.dtos;

import deliveryservice.deliveryservice.servicesproject.entity.DeliveryStatus;
import deliveryservice.deliveryservice.servicesproject.entity.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckOutOrder {
    private long cartId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private long price;
    private UserDto userDto;
    private DeliveryStatus status;
   private String message;

}
