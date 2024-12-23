package cartservice.service;

import cartservice.dtos.CartItemResponseDto;
import cartservice.dtos.CartRequestDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.dtos.ProductResponseDto;
import cartservice.entity.UserDetails;
import cartservice.userdtos.UserResponseDto;

import java.util.List;

public interface IcartServices {
//    UserDetails createUser(String userID);
    CartResposneDtos addItemToCart(CartRequestDto dto);
    CartResposneDtos removeItemFromCart(String userId, long productID);
    CartResposneDtos confirmCart(String userId);
    boolean deleteUser(long id);
    CartResposneDtos getById(long id);
    ProductResponseDto getByIds(long id);
    List<CartItemResponseDto>getAllCartItems();
    CartItemResponseDto getCartItemById(String userId);
    String getUserRoles();
    UserDetails getUser(String userEmail);

}

