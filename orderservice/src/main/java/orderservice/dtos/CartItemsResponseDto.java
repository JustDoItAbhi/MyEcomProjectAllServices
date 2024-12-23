package orderservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemsResponseDto {
    private long productId;
    private String productName;
    private int quantity;
    private double price;
}