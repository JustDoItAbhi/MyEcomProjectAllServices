package cartservice.dtos;

import cartservice.entity.CartStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartResposneDtos {// CART RESPONSE DTO
    private CartStatus cartStatus;
    private String email;
    private long cartId;
    private List<CartItemResponseDto> items = new ArrayList<>();
    private long total;
    private LocalDateTime cartCreatedTime;
    private int balanceStock;

// GETTERS AND SETTES
    public CartStatus getCartStatus() {
        return cartStatus;
    }

    public void setCartStatus(CartStatus cartStatus) {
        this.cartStatus = cartStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBalanceStock() {
        return balanceStock;
    }

    public void setBalanceStock(int balanceStock) {
        this.balanceStock = balanceStock;
    }

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
