package cartservice.mapper;

import cartservice.dtos.CartItemResponseDto;
import cartservice.entity.CartItems;

public class CartItemMapper {
    public static CartItemResponseDto fromcartItems(CartItems cartItems){// MAPPED CARTITEMS TO CARTITEM RESPONSE DTO
        CartItemResponseDto responseDto=new CartItemResponseDto();
        responseDto.setCartId(cartItems.getId());
        responseDto.setPrice(cartItems.getPrice());
        responseDto.setQuantity(cartItems.getQuantity());
        responseDto.setProductId(cartItems.getProductId());
        responseDto.setProductName(cartItems.getProductName());
        return responseDto;
    }

}
