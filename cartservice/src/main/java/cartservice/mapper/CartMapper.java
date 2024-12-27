package cartservice.mapper;

import cartservice.dtos.CartItemResponseDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.entity.CartItems;
import cartservice.entity.Carts;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {
    public static CartResposneDtos fromCart(Carts carts){
        CartResposneDtos resposneDtos=new CartResposneDtos();
        resposneDtos.setUserId(carts.getId());
        resposneDtos.setTotal(carts.getTotal());
        resposneDtos.setCartCreatedTime(carts.getCartCreatedTime());
        List<CartItemResponseDto>dto=new ArrayList<>();
        for(CartItems cartItems: carts.getItems()){
            dto.add(Mapper.fromcartItems(cartItems));
        }
        resposneDtos.setItems(dto);
    return resposneDtos;
    }
}
