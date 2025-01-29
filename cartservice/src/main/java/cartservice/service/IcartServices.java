package cartservice.service;

import cartservice.client.UserResponseDto;
import cartservice.dtos.CartItemResponseDto;
import cartservice.dtos.CartRequestDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.dtos.ProductResponseDto;
import cartservice.securityconfigrations.expcetions.expectionsfiles.CartNotFoundException;


import java.util.List;

public interface IcartServices {
//    UserDetails createUser(String userID);
    CartResposneDtos addItemToCart( String email,CartRequestDto dto) throws CartNotFoundException;
    CartResposneDtos removeItemFromCart(long userId, long productID) throws CartNotFoundException;
    CartResposneDtos confirmCart(long userId);
    CartResposneDtos getById(long id) throws CartNotFoundException;
    ProductResponseDto getProductByIds(long id);
    List<CartItemResponseDto>getAllCartItems() throws CartNotFoundException;
    CartItemResponseDto getCartItemById(String userId);
    List<ProductResponseDto> getAllProducts();
    boolean deleteCart(long cartId);
    UserResponseDto testUser(String email);





}

