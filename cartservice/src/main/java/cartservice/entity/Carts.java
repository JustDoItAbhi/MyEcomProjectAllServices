package cartservice.entity;

import cartservice.repository.CartItemsRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Carts extends BaseModels{
    private String userId;
    @OneToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<CartItems> items = new ArrayList<>();
    private double total;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItems> getItems() {
        return items;
    }

    public void setItems(List<CartItems> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
