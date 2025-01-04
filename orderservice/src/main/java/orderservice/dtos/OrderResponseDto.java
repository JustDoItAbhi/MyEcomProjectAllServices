package orderservice.dtos;

import lombok.Getter;
import lombok.Setter;
import orderservice.entity.OrderStatus;
import orderservice.entity.UserDetails;

@Getter
@Setter
public class OrderResponseDto {
    private long orderid;
    private long cartId;
    private OrderStatus  orderStatus;
    private long price;
}
