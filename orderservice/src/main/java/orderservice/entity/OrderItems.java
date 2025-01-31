package orderservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class OrderItems extends BaseModels{// ORDER ITEM ENTITY CLASS
    private long productId;
    private String productName;
    private int quantity;
    private double price;
}
