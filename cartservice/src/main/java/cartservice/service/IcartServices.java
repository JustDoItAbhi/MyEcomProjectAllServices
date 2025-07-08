package cartservice.service;

import cartservice.client.dto.UserResponseDto;
import cartservice.dtos.CartItemResponseDto;
import cartservice.dtos.CartRequestDto;
import cartservice.dtos.CartResposneDto;
import cartservice.dtos.CartResposneDtos;
import cartservice.client.dto.ProductResponseDto;
import cartservice.expcetions.expectionsfiles.CartNotFoundException;


import java.util.List;

public interface IcartServices {
//    UserDetails createUser(String userID);
    CartResposneDtos addItemToCart( String email,CartRequestDto dto) throws CartNotFoundException;// ADD TO CART
    CartResposneDtos removeItemFromCart(long userId, long productID) throws CartNotFoundException;// REMOVE PRODUCT FROM CART
    CartResposneDto confirmCart(long userId);// CONFIRM CART
    CartResposneDtos getById(long id) throws CartNotFoundException;// GET CART BY ITS ID
    ProductResponseDto getProductByIds(long id);// GET PRODUCT BY ITS ID (FOR TESTING)
    List<CartItemResponseDto>getAllCartItems() throws CartNotFoundException;// GET ALL CART ITEAMS FOR INVENTORY
    CartItemResponseDto getCartItemById(String userId);// GET CART ITEAM BY ITS ID
    List<ProductResponseDto> getAllProducts();// GET ALL PRODUCTS (TESTING)
    boolean deleteCart(long cartId);// DELETE CART BY CART ID
    UserResponseDto testUser(String email);// TESTING USER
    CartResposneDto addToCart( CartRequestDto dto);





}

