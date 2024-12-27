package cartservice.dtos;

import cartservice.entity.CartItems;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartResposneDtos {
    private long userId;
    private List<CartItemResponseDto> items = new ArrayList<>();
    private long total;
    private LocalDateTime cartCreatedTime;

    public LocalDateTime getCartCreatedTime() {
        return cartCreatedTime;
    }

    public void setCartCreatedTime(LocalDateTime cartCreatedTime) {
        this.cartCreatedTime = cartCreatedTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
