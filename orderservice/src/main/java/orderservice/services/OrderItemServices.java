package orderservice.services;

import orderservice.dtos.CheckOutOrder;
import orderservice.dtos.OrderResponseDto;
import orderservice.entity.Orders;
import orderservice.entity.UserDetails;
import orderservice.exceptions.SignUpException;
import orderservice.users.userdtos.UserResponseDto;

public interface OrderItemServices {// ORDER ITEM SERVICE LAYER AND STRATRGY DEGIN PATTERN
   OrderResponseDto getCartItems(long cartId)throws SignUpException;// // GET CART BY ITEMS
   boolean deleteOrder(long id);// DELETE ORDER
   CheckOutOrder userLoginOrSignUp(long cartiD, String email);
   Orders getOrderById(long id);// GET ORDER BY ID
   String getUserRoles();// OPTIONAL USER ROLE CHECK
}
