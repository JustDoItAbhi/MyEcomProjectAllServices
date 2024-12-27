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
    private List<CartItems> item = new ArrayList<>();
    private double totalprices;

}
