package orderservice.services;

import orderservice.dtos.OrderResponseDto;
import orderservice.entity.Orders;
import orderservice.exceptions.SignUpException;

public interface OrderItemServices {// ORDER ITEM SERVICE LAYER AND STRATRGY DEGIN PATTERN
   OrderResponseDto getCartItems(long cartId)throws SignUpException;// // GET CART BY ITEMS
   boolean deleteOrder(long id);// DELETE ORDER
   Orders getOrderById(long id);// GET ORDER BY ID
   String getUserRoles();// OPTIONAL USER ROLE CHECK
}
