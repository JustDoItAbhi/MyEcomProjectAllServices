package orderservice.services;

import orderservice.dtos.OrderResponseDto;
import orderservice.entity.Orders;
import orderservice.exceptions.SignUpException;

public interface OrderItemServices {
   OrderResponseDto getCartItems(String UserEmail,long cartId)throws SignUpException;
   boolean deleteOrder(long id);
   Orders getOrderById(long id);
   String getUserRoles();
}
