package orderservice.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import orderservice.entity.OrderStatus;
import orderservice.users.userdtos.UserResponseDto;

@Getter
@Setter
public class CheckOutOrder {
    private long cartId;
    private OrderStatus orderStatus;
    private long price;
    private UserDto userDto;
}
