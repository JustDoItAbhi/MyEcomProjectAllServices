package orderservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Orders extends BaseModels{
    private long cartId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private long price;

}
