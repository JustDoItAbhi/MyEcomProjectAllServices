package cartservice.service;

import cartservice.dtos.CartItemResponseDto;
import cartservice.dtos.CartRequestDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.dtos.ProductResponseDto;
import expcetions.CartNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.List;

public interface IcartServices {
//    UserDetails createUser(String userID);
    CartResposneDtos addItemToCart(CartRequestDto dto);
    CartResposneDtos removeItemFromCart(long userId, long productID);
    CartResposneDtos confirmCart(long userId);
    CartResposneDtos getById(long id) throws CartNotFoundException;
    ProductResponseDto getByIds(long id);
    List<CartItemResponseDto>getAllCartItems();
    CartItemResponseDto getCartItemById(String userId);
    List<ProductResponseDto> getAllProducts();
    boolean deleteCart(long cartId);





}

