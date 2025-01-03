package cartservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Carts extends BaseModels{
    @OneToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<CartItems> items = new ArrayList<>();
    private long total;
    private LocalDateTime cartCreatedTime;

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




}
