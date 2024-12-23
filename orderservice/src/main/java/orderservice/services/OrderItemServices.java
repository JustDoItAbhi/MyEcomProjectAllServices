package orderservice.services;

import orderservice.dtos.OrderResponseDto;
import orderservice.entity.Orders;

public interface OrderItemServices {
   OrderResponseDto getCartItems(String userId);
   boolean deleteOrder(long id);
   Orders getById(long id);
   String getUserRoles();
}
