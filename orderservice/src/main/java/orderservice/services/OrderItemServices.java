package orderservice.services;

import orderservice.dtos.OrderResponseDto;
import orderservice.entity.Orders;

public interface OrderItemServices {
   OrderResponseDto getCartItems(String UserEmail,long cartId);
   boolean deleteOrder(long id);
   Orders getOrderById(long id);
   String getUserRoles();
}
