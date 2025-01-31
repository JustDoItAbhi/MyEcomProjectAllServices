package orderservice.mappers;

import orderservice.dtos.CartResposneDtos;
import orderservice.dtos.OrderResponseDto;
import orderservice.entity.Orders;

public class OrderMapper {
    public static Orders fromcartResponseDto(CartResposneDtos dtos){//NOT USED
        Orders orders=new Orders();
        orders.setCartId(dtos.getCartId());
        orders.setPrice(dtos.getTotal());

        return orders;
    }
    public static OrderResponseDto fromEntity(Orders order){// MAPPING FROM ORDER ENTITY TO RESPONSE DTO
        OrderResponseDto responseDto=new OrderResponseDto();
        responseDto.setOrderid(order.getId());
        responseDto.setCartId(order.getCartId());
        responseDto.setOrderStatus(order.getOrderStatus());
        responseDto.setPrice(order.getPrice());
        return responseDto;
    }
}
