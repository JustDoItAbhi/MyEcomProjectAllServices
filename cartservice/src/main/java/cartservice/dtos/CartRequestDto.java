package cartservice.dtos;

import cartservice.entity.CartItems;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CartRequestDto {
    private String userId;
    private List<CartItems> item = new ArrayList<>();
    private double totalprices;

//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public List<CartItems> getItem() {
//        return item;
//    }
//
//    public void setItem(List<CartItems> item) {
//        this.item = item;
//    }
//
//    public double getTotalprices() {
//        return totalprices;
//    }
//
//    public void setTotalprices(double totalprices) {
//        this.totalprices = totalprices;
//    }
}
