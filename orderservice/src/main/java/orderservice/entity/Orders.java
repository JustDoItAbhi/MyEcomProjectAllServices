package orderservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Entity
public class Orders extends BaseModels{
    private String userId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private double price;
    //    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    private List<OrderItems> items;
}
