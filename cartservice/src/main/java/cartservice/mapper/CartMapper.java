package cartservice.mapper;

import cartservice.dtos.CartItemResponseDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.entity.CartItems;
import cartservice.entity.Carts;

import java.util.ArrayList;
import java.util.List;

public class CartMapper {
    public static CartResposneDtos fromCart(Carts carts){// MAPPING CART ENTITY TO RESPONSE DTO
        CartResposneDtos resposneDtos=new CartResposneDtos();
        resposneDtos.setEmail(carts.getEmail());
        resposneDtos.setCartId(carts.getId());
        resposneDtos.setTotal(carts.getTotal());
        resposneDtos.setCartStatus(carts.getCartStatus());
        resposneDtos.setBalanceStock(carts.getLeftItemStock());
        resposneDtos.setCartCreatedTime(carts.getCartCreatedTime());
        List<CartItemResponseDto>dto=new ArrayList<>();
        for(CartItems cartItems: carts.getItems()){
            dto.add(CartItemMapper.fromcartItems(cartItems));
        }
        resposneDtos.setItems(dto);
    return resposneDtos;
    }
}
