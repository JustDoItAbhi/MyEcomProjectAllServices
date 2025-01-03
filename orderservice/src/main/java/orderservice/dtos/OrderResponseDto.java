package orderservice.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import orderservice.entity.OrderStatus;
@Getter
@Setter
public class OrderResponseDto {
    private long orderid;
    private long cartId;
    private OrderStatus  orderStatus;
    private long price;
}
