package cartservice.mapper;

import cartservice.dtos.CartItemResponseDto;
import cartservice.entity.CartItems;

public class Mapper {
    public static CartItemResponseDto fromcartItems(CartItems cartItems){
        CartItemResponseDto responseDto=new CartItemResponseDto();
        responseDto.setCartId(cartItems.getId());
        responseDto.setPrice(cartItems.getPrice());
        responseDto.setQuantity(cartItems.getQuantity());
        responseDto.setProductId(cartItems.getProductId());
        responseDto.setProductName(cartItems.getProductName());
        return responseDto;
    }

}
