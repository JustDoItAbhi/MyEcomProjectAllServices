package deliveryservice.deliveryservice.servicesproject.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartResposneDtos {
    private long cartId;
    private List<CartItemResponseDto> items = new ArrayList<>();
    private long total;
    private LocalDateTime cartCreatedTime;

    public LocalDateTime getCartCreatedTime() {
        return cartCreatedTime;
    }

    public void setCartCreatedTime(LocalDateTime cartCreatedTime) {
        this.cartCreatedTime = cartCreatedTime;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public List<CartItemResponseDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponseDto> items) {
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
