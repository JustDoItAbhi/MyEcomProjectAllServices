package cartservice.dtos;

import cartservice.entity.CartItems;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class CartResposneDtos {
    private String userId;
    private List<CartItemResponseDto> items = new ArrayList<>();
    private double total;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItemResponseDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponseDto> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
