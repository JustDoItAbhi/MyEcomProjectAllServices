package deliveryservice.deliveryservice.servicesproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckOutOrder {
    private long cartId;
    private deliveryservice.deliveryservice.servicesproject.dto.OrderStatus orderStatus;
    private long price;
    private UserDto userDto;
}
