package orderservice.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import orderservice.entity.OrderStatus;
@Getter

@Setter
public class OrderResponseDto {
    private long id;
    private String userId;
    private OrderStatus  orderStatus;
    private double price;
}
