package orderservice.mappers;

import orderservice.dtos.CartResposneDtos;
import orderservice.dtos.OrderResponseDto;
import orderservice.entity.Orders;

public class OrderMapper {
    public static Orders fromcartResponseDto(CartResposneDtos dtos){
        Orders orders=new Orders();
        orders.setCartId(dtos.getCartId());
        orders.setPrice(dtos.getTotal());
//        List<OrderItems>itemsList=new ArrayList<>();
//        for(CartItemsResponseDto itemsResponseDto: dtos.getItems()){
//            OrderItems items=new OrderItems();
//            items.setProductName(itemsResponseDto.getProductName());
//            items.setPrice(itemsResponseDto.getPrice());
//            items.setQuantity(itemsResponseDto.getQuantity());
//            items.setProductId(itemsResponseDto.getProductId());
//            itemsList.add(items);
//        }
//        orders.setItems(itemsList);
        return orders;
    }
    public static OrderResponseDto fromEntity(Orders order){
        OrderResponseDto responseDto=new OrderResponseDto();
        responseDto.setOrderid(order.getId());
        responseDto.setCartId(order.getCartId());
        responseDto.setOrderStatus(order.getOrderStatus());
        responseDto.setPrice(order.getPrice());

        return responseDto;
    }
}
