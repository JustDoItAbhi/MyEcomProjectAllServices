package cartservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carts extends BaseModels{
    private String email;
    @OneToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<CartItems> items = new ArrayList<>();
    private long total;
    private LocalDateTime cartCreatedTime;
    private int leftItemStock;
    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus;

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

    public LocalDateTime getCartCreatedTime() {
        return cartCreatedTime;
    }

    public void setCartCreatedTime(LocalDateTime cartCreatedTime) {
        this.cartCreatedTime = cartCreatedTime;
    }
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<CartItems> getItems() {
        return items;
    }

    public void setItems(List<CartItems> items) {
        this.items = items;
    }

    public int getLeftItemStock() {
        return leftItemStock;
    }

    public void setLeftItemStock(int leftItemStock) {
        this.leftItemStock = leftItemStock;
    }
}
