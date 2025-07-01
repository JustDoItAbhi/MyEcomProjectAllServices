package orderservice.extraclassestomimplementlater;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemsResponseDto {// CART ITEAM RESPONSE DTO USED FOR CART REST TEMPLATE SERVICE
    private long productId;
    private String productName;
    private int quantity;
    private double price;
}